package com.testeducation.ui.screen.home.library

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
import com.testeducation.logic.screen.tests.library.tests.TestLibrarySideEffect
import com.testeducation.logic.screen.tests.library.tests.TestLibraryState
import com.testeducation.screen.tests.base.TestsDefaults
import com.testeducation.screen.tests.library.tests.TestLibraryViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentLibraryBinding
import com.testeducation.ui.delegates.tests.createTestLoadingDelegate
import com.testeducation.ui.delegates.tests.createTestShortAdapterDelegate
import com.testeducation.ui.delegates.tests.createThemeShortAdapterDelegate
import com.testeducation.ui.screen.tests.list.TestShortDiffUtil
import com.testeducation.ui.utils.addPageScrollListener
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

class TestLibraryFragment : ViewModelHostFragment<TestLibraryViewModel, FragmentLibraryBinding>(
    TestLibraryViewModel::class,
    FragmentLibraryBinding::inflate
) {

    private val testsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortAdapterDelegate(viewModel::toggleTestLike),
            createTestLoadingDelegate()
        )
    }

    private val themesAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(ThemeShortUI::id),
            createThemeShortAdapterDelegate(onClick = viewModel::onThemeChanged)
        )
    }

    private var scrollListener: RecyclerView.OnScrollListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = requireContext().loadColor(R.color.colorBlue)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun render(state: TestLibraryState) = binding {
        val orderFieldText = when (state.selectedSortField) {
            TestOrderFieldUI.TITLE -> getString(R.string.tests_list_sort_field_title)
            TestOrderFieldUI.CREATION -> getString(R.string.tests_list_sort_field_creation_date)
            TestOrderFieldUI.QUESTIONS -> getString(R.string.tests_list_sort_field_questions)
        }

        sortLabel.text = orderFieldText

        testsAdapter.items = state.tests
        themesAdapter.items = state.themes

        themesRecycler.isInvisible = state.isThemesLoading
        themesShimmer.isShimmerHide = !state.isThemesLoading

        globalProgress.isGone = !state.isTestsLoading
        testsRecycler.isInvisible = state.isTestsLoading
    }

    private fun onSideEffect(sideEffect: TestLibrarySideEffect) = when (sideEffect) {
        TestLibrarySideEffect.OnLoadReady -> addScrollListener()
    }

    private fun setupViews() = binding {
        testsRecycler.adapter = testsAdapter
        testsRecycler.disableChangeAnimation()

        themesRecycler.adapter = themesAdapter
        themesRecycler.disableChangeAnimation()
    }

    private fun setupListeners() = binding {
        testsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > TestsDefaults.SCROLL_OFFSET) viewModel.onScrollToTop()

                if (dy < -TestsDefaults.SCROLL_OFFSET) viewModel.onScrollToBottom()

                if (!recyclerView.canScrollVertically(-1)) {
                    viewModel.onScrollToBottom()
                }
            }
        })
        btnFilter.setClickListener(viewModel::openFilters)
        filtersLabel.setClickListener(viewModel::openFilters)
        addScrollListener()
    }

    private fun addScrollListener() = binding {
        if (scrollListener != null) return@binding
        scrollListener = testsRecycler.addPageScrollListener(TestsDefaults.TESTS_THRESHOLD) {
            removeScrollListener()
            viewModel.loadNextPage()
        }
    }

    private fun removeScrollListener() = binding {
        if (scrollListener != null) {
            testsRecycler.removeOnScrollListener(scrollListener!!)
        }
    }

}
