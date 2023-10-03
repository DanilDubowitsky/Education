package com.testeducation.ui.screen.tests.creation.time

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.TimeQuestionUi
import com.testeducation.logic.screen.tests.creation.question.creation.time.TimeQuestionState
import com.testeducation.screen.tests.creation.question.creation.time.TimeQuestionViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTimeQuestionBinding
import com.testeducation.ui.delegates.tests.question.timeQuestionItemDelegate
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.simpleDiffUtil

class TimeQuestionDialog:  ViewModelHostBottomSheetDialog<DialogTimeQuestionBinding, TimeQuestionViewModel>(
    TimeQuestionViewModel::class,
    DialogTimeQuestionBinding::inflate
) {

    override val isHideAble: Boolean
        get() = false

    private val timeQuestionAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(TimeQuestionUi::time),
            timeQuestionItemDelegate(viewModel::submit)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observe(this, ::render)
        binding.rvTime {
            adapter = timeQuestionAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }
        binding.btnSave.setOnClickListener {
            viewModel.exit()
            dismiss()
        }
    }

    private fun render(state: TimeQuestionState) = binding.invoke {
        timeQuestionAdapter.items = state.timeList
    }

}