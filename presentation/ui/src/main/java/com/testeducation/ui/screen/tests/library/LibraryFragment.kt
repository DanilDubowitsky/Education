package com.testeducation.ui.screen.tests.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewpager2.widget.MarginPageTransformer
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.screen.tests.library.LibraryState
import com.testeducation.screen.tests.library.LibraryViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentLibraryBinding
import com.testeducation.ui.delegates.tests.createTestShortPagerDelegate
import com.testeducation.ui.screen.tests.list.TestShortDiffUtil
import com.testeducation.ui.utils.dp
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class LibraryFragment : ViewModelHostFragment<LibraryViewModel, FragmentLibraryBinding>(
    LibraryViewModel::class,
    FragmentLibraryBinding::inflate
) {

    private val publishedTestsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortPagerDelegate()
        )
    }

    private val passedTestsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortPagerDelegate()
        )
    }

    private val draftsTestsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortPagerDelegate()
        )
    }

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
        observeData()
        setupViews()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun setupListeners() = binding {
        txtPublished.setClickListener(viewModel::openPublishedTests)
        txtDrafts.setClickListener(viewModel::openDraftTests)
        txtPassed.setClickListener(viewModel::openPassedTests)
    }

    private fun render(state: LibraryState) = binding {
        loadingShimmer.isShimmerHide = !state.isLoading
        contentGroop.isVisible = !state.isLoading

        draftsTestsAdapter.items = state.draftsTests
        passedTestsAdapter.items = state.passedTests
        publishedTestsAdapter.items = state.publishedTests

        if (!state.isLoading) {
            renderSectionsVisibility(
                state.draftsTests.isEmpty(),
                state.publishedTests.isEmpty(),
                state.passedTests.isEmpty()
            )
        }
    }

    private fun FragmentLibraryBinding.renderSectionsVisibility(
        isDraftsEmpty: Boolean,
        isPublishedEmpty: Boolean,
        isPassedEmpty: Boolean
    ) {
        draftsPager.isVisible = !isDraftsEmpty
        imgDrafts.isVisible = !isDraftsEmpty
        txtDrafts.isVisible = !isDraftsEmpty

        publishedPager.isVisible = !isPublishedEmpty
        txtPublished.isVisible = !isPublishedEmpty
        imgPublishedArrow.isVisible = !isPublishedEmpty

        passedPager.isVisible = !isPassedEmpty
        imgPassed.isVisible = !isPassedEmpty
        txtPassed.isVisible = !isPassedEmpty
    }

    private fun setupViews() = binding {
        passedPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            setPadding(LEFT_PAGER_PADDING.dp, 0, PAGES_PADDING.dp, 0)
            setPageTransformer(MarginPageTransformer(PAGE_MARGIN.dp))
            adapter = passedTestsAdapter
        }

        draftsPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            setPadding(LEFT_PAGER_PADDING.dp, 0, PAGES_PADDING.dp, 0)
            setPageTransformer(MarginPageTransformer(PAGE_MARGIN.dp))
            adapter = draftsTestsAdapter
        }

        publishedPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            setPadding(LEFT_PAGER_PADDING.dp, 0, PAGES_PADDING.dp, 0)
            setPageTransformer(MarginPageTransformer(PAGE_MARGIN.dp))
            adapter = publishedTestsAdapter
        }
    }

    private companion object {
        const val LEFT_PAGER_PADDING = 24
        const val PAGE_MARGIN = 12
        const val PAGES_PADDING = 66
    }
}
