package com.example.ui.screen.tests

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import com.example.logic.model.test.TestShortUI
import com.example.logic.model.theme.ThemeShortUI
import com.example.logic.screen.tests.TestsState
import com.example.screen.tests.TestsViewModel
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentTestsBinding
import com.example.ui.delegates.tests.createTestShortAdapterDelegate
import com.example.ui.delegates.tests.createThemeShortAdapterDelegate
import com.example.ui.utils.invoke
import com.example.ui.utils.isShimmerHide
import com.example.ui.utils.observe
import com.example.ui.utils.simpleDiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class TestsFragment : ViewModelHostFragment<TestsViewModel, FragmentTestsBinding>(
    TestsViewModel::class,
    FragmentTestsBinding::inflate
) {

    private val testsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(TestShortUI::id),
            createTestShortAdapterDelegate()
        )
    }

    private val themesAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(ThemeShortUI::id),
            createThemeShortAdapterDelegate { themeId ->
                viewModel.onThemeChanged(themeId)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        observeData()
    }

    private fun observeData() {
        viewModel.observe(this, ::render)
    }

    private fun setupRecycler() = with(binding) {
        testsRecycler.adapter = testsAdapter
        themesRecycler.adapter = themesAdapter
    }

    private fun render(state: TestsState) = binding {
        bindProfile(state)
        bindThemes(state)
        //rootGroup.isGone = state.isTestsLoading
        globalProgress.isGone = !state.isTestsLoading
        testsAdapter.items = state.tests
    }

    private fun FragmentTestsBinding.bindThemes(state: TestsState) {
        themesRecycler.isInvisible = state.isThemesLoading
        themesShimmer.isShimmerHide = !state.isThemesLoading
        themesAdapter.items = state.themes
    }

    private fun FragmentTestsBinding.bindProfile(state: TestsState) {
        imgAvatar.isGone = state.isProfileLoading
        txtUserName.isGone = state.isProfileLoading
        appBarShimmer.isShimmerHide = !state.isProfileLoading
        txtUserName.text = state.userName
    }

}
