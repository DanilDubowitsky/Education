package com.testeducation.ui.screen.tests

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.screen.tests.TestsState
import com.testeducation.logic.screen.theme.ThemeShortUI
import com.testeducation.screen.tests.TestsViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestsBinding
import com.testeducation.ui.delegates.tests.createTestShortAdapterDelegate
import com.testeducation.ui.delegates.tests.createThemeShortAdapterDelegate
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.simpleDiffUtil

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
            createThemeShortAdapterDelegate()
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
        categoriesRecycler.adapter = themesAdapter
    }

    private fun render(state: TestsState) = binding {
        txtUserName.text = state.userName
        rootGroup.isGone = state.isLoading
        globalProgress.isGone = !state.isLoading
        testsAdapter.items = state.tests
        themesAdapter.items = state.themes
    }

}
