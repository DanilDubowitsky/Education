package com.testeducation.ui.screen.tests.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.logic.screen.tests.list.TestsState
import com.testeducation.screen.tests.list.TestsViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestsBinding
import com.testeducation.ui.delegates.tests.createTestShortAdapterDelegate
import com.testeducation.ui.delegates.tests.createThemeShortAdapterDelegate
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
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
            createThemeShortAdapterDelegate { themeId ->
                viewModel.onThemeChanged(themeId)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        observeData()
        setupListeners()
    }

    private fun observeData() {
        viewModel.observe(this, ::render)
    }

    private fun setupRecycler() = with(binding) {
        testsRecycler.adapter = testsAdapter
        themesRecycler.adapter = themesAdapter
    }

    private fun render(state: TestsState) = binding {
        val orderFieldText = when (state.selectedSortField) {
            TestOrderFieldUI.TITLE -> getString(R.string.tests_list_sort_field_title)
            TestOrderFieldUI.CREATION -> getString(R.string.tests_list_sort_field_creation_date)
            TestOrderFieldUI.QUESTIONS -> getString(R.string.tests_list_sort_field_questions)
        }

        sortLabel.text = orderFieldText

        bindProfile(state)
        bindThemes(state)
        bindTests(state)
    }

    private fun setupListeners() = with(binding) {
        testsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > SCROLL_OFFSET) viewModel.onScrollToTop()

                if (dy < -SCROLL_OFFSET) viewModel.onScrollToBottom()

                if (!recyclerView.canScrollVertically(-1)) {
                    viewModel.onScrollToBottom()
                }
            }
        })
        filtersLabel.setClickListener(viewModel::openFiltersScreen)
        btnFilter.setClickListener(viewModel::openFiltersScreen)
    }

    private fun FragmentTestsBinding.bindTests(state: TestsState) {
        globalProgress.isGone = !state.isTestsLoading
        testsAdapter.items = state.tests
        testsRecycler.isInvisible = state.isTestsLoading
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

    private companion object {
        const val SCROLL_OFFSET = 20
    }

}
