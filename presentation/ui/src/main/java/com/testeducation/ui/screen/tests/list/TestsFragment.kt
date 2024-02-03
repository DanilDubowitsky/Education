package com.testeducation.ui.screen.tests.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.logic.screen.tests.list.TestsSideEffect
import com.testeducation.logic.screen.tests.list.TestsState
import com.testeducation.screen.tests.base.TestsDefaults
import com.testeducation.screen.tests.list.TestsViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestsBinding
import com.testeducation.ui.delegates.tests.createTestLoadingDelegate
import com.testeducation.ui.delegates.tests.createTestShortAdapterDelegate
import com.testeducation.ui.delegates.tests.createThemeShortAdapterDelegate
import com.testeducation.ui.utils.addPageScrollListener
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

class TestsFragment : ViewModelHostFragment<TestsViewModel, FragmentTestsBinding>(
    TestsViewModel::class,
    FragmentTestsBinding::inflate
) {

    private val testsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortAdapterDelegate(
                viewModel::toggleTestLike,
                viewModel::onTestClick
            ),
            createTestLoadingDelegate()
        )
    }

    private val themesAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(ThemeShortUI::id),
            createThemeShortAdapterDelegate(
                R.color.selector_color_chip_white,
                onClick = viewModel::onThemeChanged
            )
        )
    }

    private var pageLoadingListener: RecyclerView.OnScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = requireContext().loadColor(
            android.R.color.transparent
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        observeData()
        setupListeners()
    }

    private fun observeData() {
        viewModel.observe(this, ::render, ::onSideEffect)
    }

    private fun setupRecycler() = with(binding) {
        testsRecycler.adapter = testsAdapter
        themesRecycler.adapter = themesAdapter
        testsRecycler.disableChangeAnimation()
    }

    private fun onSideEffect(sideEffect: TestsSideEffect) = when (sideEffect) {
        TestsSideEffect.OnLoadReady -> {
            binding.refreshLayout.isRefreshing = false
            addTestsLoadingListener()
        }
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

        imgAvatar.setImageResource(state.avatarDrawableId)
    }

    private fun addTestsLoadingListener() {
        if (pageLoadingListener == null) {
            pageLoadingListener = binding.testsRecycler.addPageScrollListener(
                TestsDefaults.TESTS_THRESHOLD
            ) {
                removeTestsLoadingListener()
                viewModel.loadNextPage()
            }
        }
    }

    private fun removeTestsLoadingListener() {
        if (pageLoadingListener != null) {
            binding.testsRecycler.removeOnScrollListener(pageLoadingListener!!)
            pageLoadingListener = null
        }
    }

    private fun setupListeners() = with(binding) {
        testsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > TestsDefaults.SCROLL_OFFSET) viewModel.onScrollToTop()

                if (dy < -TestsDefaults.SCROLL_OFFSET) viewModel.onScrollToBottom()

                if (!recyclerView.canScrollVertically(-1)) {
                    viewModel.onScrollToBottom()
                }
            }
        })
        filtersLabel.setClickListener(viewModel::openFiltersScreen)
        btnFilter.setClickListener(viewModel::openFiltersScreen)
        pageLoadingListener = testsRecycler.addPageScrollListener(
            TestsDefaults.TESTS_THRESHOLD,
            viewModel::loadNextPage
        )
        txtEnterCode.setClickListener(viewModel::openEnterCodeScreen)
        refreshLayout.setOnRefreshListener(viewModel::refresh)
    }

    private fun FragmentTestsBinding.bindTests(state: TestsState) {
        testsAdapter.items = state.tests
        testsRecycler.isInvisible = state.isTestsLoading
        shimmerLoading.isShimmerHide = !state.isTestsLoading
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
