package com.testeducation.ui.screen.tests.statistic

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState
import com.testeducation.screen.tests.statistic.TestPassStatisticViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPassStatisticBinding
import com.testeducation.ui.delegates.tests.question.choiceAnsweredQuestionDelegate
import com.testeducation.ui.delegates.tests.question.matchAnsweredQuestionDelegate
import com.testeducation.ui.delegates.tests.question.orderAnsweredQuestionDelegate
import com.testeducation.ui.delegates.tests.question.textAnswerDelegate
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil
import com.testeducation.utils.MINUTES_SECONDS_FORMAT
import com.testeducation.utils.formatDateInSeconds

class TestPassStatisticFragment :
    ViewModelHostFragment<TestPassStatisticViewModel, FragmentTestPassStatisticBinding>(
        TestPassStatisticViewModel::class,
        FragmentTestPassStatisticBinding::inflate
    ) {

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(AnsweredQuestionUI::id),
            orderAnsweredQuestionDelegate(),
            choiceAnsweredQuestionDelegate(),
            matchAnsweredQuestionDelegate(
                viewModel::changeItemExpandState,
                viewModel::changeTrueAnswerExpandState
            ),
            textAnswerDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().loadColor(
            android.R.color.transparent
        )
        observeData()
        setupListeners()
        setupViews()
    }

    private fun setupViews() {
        binding.resultsRecycler.adapter = adapter
    }

    private fun setupListeners() = binding {
        btnBack.setClickListener(viewModel::exit)
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestPassStatisticState) = binding {
        requireActivity().window.statusBarColor = Color.parseColor(state.testColor)
        circularProgress.setVisibility(state.isLoading)
        testPassTimeDescription.isGone = state.passTime <= 0L || state.isLoading
        testPassTime.isGone = state.passTime <= 0L || state.isLoading
        testPassResultTitle.isGone = state.isLoading
        statsLayout.isGone = state.isLoading
        resultsRecycler.isGone = state.isLoading

        topAppBar.backgroundTintList = ColorStateList.valueOf(Color.parseColor(state.testColor))
        testTitle.text = state.testTitle
        txtTrueAnswersCount.text = state.trueAnswers.toString()
        txtFalseAnswersCount.text = state.falseAnswers.toString()
        testPassTime.text = formatDateInSeconds(MINUTES_SECONDS_FORMAT, state.passTime)
        if (!state.isSuccess) {
            testPassResultTitle.text = getString(R.string.test_pass_test_failed_description)
        }
        adapter.items = state.questions
    }

}
