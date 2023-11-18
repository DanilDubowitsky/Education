package com.testeducation.ui.screen.tests.statistic

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState
import com.testeducation.screen.tests.statistic.TestPassStatisticViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPassStatisticBinding
import com.testeducation.ui.delegates.tests.question.choiceAnsweredQuestionDelegate
import com.testeducation.ui.delegates.tests.question.matchAnsweredQuestionDelegate
import com.testeducation.ui.delegates.tests.question.orderAnsweredQuestionDelegate
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

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
            matchAnsweredQuestionDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun render(state: TestPassStatisticState) {
        adapter.items = state.questions
    }

}
