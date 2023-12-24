package com.testeducation.ui.screen.tests.preview

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.viewpager2.widget.MarginPageTransformer
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.screen.tests.preview.TestPreviewViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPreviewBinding
import com.testeducation.ui.delegates.tests.createTestShortPagerDelegate
import com.testeducation.ui.screen.tests.list.TestShortDiffUtil
import com.testeducation.ui.utils.dp
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isFadeGone
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class TestPreviewFragment : ViewModelHostFragment<TestPreviewViewModel, FragmentTestPreviewBinding>(
    TestPreviewViewModel::class,
    FragmentTestPreviewBinding::inflate
) {

    private val authorTestsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortPagerDelegate(
                viewModel::onTestClick,
                viewModel::onLikeClick
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().loadColor(R.color.colorDarkGreen)
        observeData()
        setupViews()
        setupListeners()
    }

    private fun setupViews() = binding {
        authorTestsPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 2
            setPadding(
                LEFT_PAGER_PADDING.dp,
                0,
                PAGES_PADDING.dp,
                0
            )
            setPageTransformer(MarginPageTransformer(PAGE_MARGIN.dp))
            adapter = authorTestsAdapter
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestPreviewState) = binding {
        shimmerLayout.isShimmerHide = !state.isLoading
        contentAppBar.isGone = state.isLoading
        rootScroll.isGone = state.isLoading
        txtUserDisplayName.text = state.creatorName
        btnShare.isGone = state.isLoading
        btnFavorite.isGone = state.isLoading
        authorTestsAdapter.items = state.authorTests
        btnPassTest.isGone = state.isLoading
        renderTestDetails(state)
    }

    private fun setupListeners() = binding {
        btnShowQuestions.setClickListener(
            needDisable = false,
            viewModel::openQuestionsScreen
        )
        txtShowMore.setClickListener(needDisable = false, viewModel::toggleDescriptionExpand)
        appBar.addOnOffsetChangedListener { _, _ ->
            val rect = Rect()
            txtTitle.getGlobalVisibleRect(rect)
            txtToolbarTitle.isFadeGone = rootAppBar.bottom < rect.bottom
        }
        btnFavorite.setClickListener(viewModel::toggleFavorite)
        btnBack.setClickListener(viewModel::exit)
        btnPassTest.setClickListener(viewModel::openTestPassingScreen)
        btnShare.setClickListener(viewModel::openCodeScreen)
    }

    private fun FragmentTestPreviewBinding.renderTestDetails(state: TestPreviewState) {
        txtTestPassTime.isGone = state.hideTestTimeLimit
        txtTestPassTime.text = state.timeLimit
        imgAvatar.setImageResource(state.avatarResource)
        imgAvatar2.setImageResource(state.avatarResource)
        if (state.isExpand) {
            txtDescription.maxLines = Int.MAX_VALUE
        } else {
            txtDescription.maxLines = MAX_DESCRIPTION_LINES
        }
        txtToolbarTitle.text = state.title
        txtDescription.text = state.description
        txtTheme.text = state.theme
        txtDate.text = state.createdDate
        txtTitle.text = state.title
        txtAuthorName.text = state.creatorName
        txtQuestionsCount.text = resources.getQuantityString(
            R.plurals.questions_count_plurals,
            state.questionsCount,
            state.questionsCount
        )
        btnShowQuestions.isGone = !state.allowPreviewQuestions
        if (state.isLiked) {
            btnFavorite.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_outline)
        }
        authorTestsPager.isGone = state.authorTests.isEmpty()
        txtAuthorTests.isGone = state.authorTests.isEmpty()
        imgAuthorTests.isGone = state.authorTests.isEmpty()

        txtShowMore.isGone = !state.isExpandButtonVisible
        txtShowMore.text = if (state.isExpand) {
            getString(R.string.test_preview_hide)
        } else {
            getString(R.string.test_preview_show_more)
        }
    }

    private companion object {
        const val MAX_DESCRIPTION_LINES = 6
        const val LEFT_PAGER_PADDING = 24
        const val PAGE_MARGIN = 12
        const val PAGES_PADDING = 66

    }

}
