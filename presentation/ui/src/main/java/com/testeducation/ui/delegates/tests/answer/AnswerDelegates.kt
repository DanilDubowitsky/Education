package com.testeducation.ui.delegates.tests.answer

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.MotionEvent
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.ui.databinding.ViewHolderAnswerChoiceBinding
import com.testeducation.ui.databinding.ViewHolderAnswerMatchPassBinding
import com.testeducation.ui.databinding.ViewHolderAnswerOrderIndicatorBinding
import com.testeducation.ui.databinding.ViewHolderAnswerOrderPassBinding
import com.testeducation.ui.listener.drag.IDragStartListener
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createChoiceAnswerDelegate(
    onSelectAnswer: (Int) -> Unit
) = simpleDelegateAdapter<AnswerUI.ChoiceAnswer, AnswerUI, ViewHolderAnswerChoiceBinding>(
    ViewHolderAnswerChoiceBinding::inflate
) {
    binding.checkBoxAnswer.setClickListener(needDisable = false) {
        onSelectAnswer(absoluteAdapterPosition)
    }
    bind {
        binding.bindChoiceAnswer(item)
    }
}

@SuppressLint("ClickableViewAccessibility")
fun createOrderAnswerDelegate(
    onDragListener: IDragStartListener
) = simpleDelegateAdapter<AnswerUI.OrderAnswer, AnswerUI, ViewHolderAnswerOrderPassBinding>(
    ViewHolderAnswerOrderPassBinding::inflate
) {
    bind {
        binding.root.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                onDragListener.oDragStarted(this)
            }
            false
        }
        binding.bindOrderAnswer(item)
    }
}

fun createMatchAnswerDelegate() = simpleDelegateAdapter<AnswerUI.MatchAnswer, AnswerUI,
        ViewHolderAnswerMatchPassBinding>(
    ViewHolderAnswerMatchPassBinding::inflate
) {
    bind {
        binding.bindMatchAnswer(item)
    }
}

fun createMatchDataDelegate() =
    simpleDelegateAdapter<TestPassingState.MatchDataUI, TestPassingState.MatchDataUI,
            ViewHolderAnswerOrderIndicatorBinding>(
        ViewHolderAnswerOrderIndicatorBinding::inflate
    ) {
        bind {
            binding.bind(item)
        }
    }

private fun ViewHolderAnswerOrderIndicatorBinding.bind(item: TestPassingState.MatchDataUI) {
    tvNumber.text = item.text
    root.backgroundTintList = ColorStateList.valueOf(item.color)
}

private fun ViewHolderAnswerOrderPassBinding.bindOrderAnswer(
    item: AnswerUI.OrderAnswer
) {
    root.backgroundTintList = ColorStateList.valueOf(item.color)
    txtAnswer.text = item.title
}

private fun ViewHolderAnswerMatchPassBinding.bindMatchAnswer(item: AnswerUI.MatchAnswer) {
    root.backgroundTintList = ColorStateList.valueOf(item.color)
    txtTitle.text = item.title
}

private fun ViewHolderAnswerChoiceBinding.bindChoiceAnswer(
    item: AnswerUI.ChoiceAnswer
) {
    root.backgroundTintList = ColorStateList.valueOf(item.color)
    txtAnswerText.text = item.title
    checkBoxAnswer.isChecked = item.isSelected
}
