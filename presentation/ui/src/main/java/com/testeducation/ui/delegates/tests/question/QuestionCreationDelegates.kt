package com.testeducation.ui.delegates.tests.question

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.MotionEvent
import androidx.core.widget.doOnTextChanged
import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultBinding
import com.testeducation.ui.databinding.ViewHolderAnswerFootPlusBinding
import com.testeducation.ui.databinding.ViewHolderAnswerMatchBinding
import com.testeducation.ui.databinding.ViewHolderAnswerWriteBinding
import com.testeducation.ui.listener.IDragStartListener
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

@SuppressLint("ClickableViewAccessibility")
fun answersDelegateDefault(
    onClickCheckTrue: (String) -> Unit,
    onClickDelete: (String) -> Unit,
    onAnswerTextChanger: (String, String) -> Unit,
    mDragStartListener: IDragStartListener,
    onSelectedElement: (String) -> Unit
) = simpleDelegateAdapter<AnswerItemUi.DefaultAnswer,
        AnswerItemUi,
        ViewHolderAnswerDefaultBinding>(
    ViewHolderAnswerDefaultBinding::inflate
) {
    bind {
        binding {
            if (etAnswer.text.isEmpty()) {
                etAnswer.setText(item.answerText)
            }
            imgDelete.setOnClickListener {
                onClickDelete(item.id)
            }
            imgCheckIcon.setOnClickListener {
                onClickCheckTrue(item.id)
            }

            imgCheckIcon.backgroundTintList =
                ColorStateList.valueOf(item.resource.isTrueColor)

            root.backgroundTintList = ColorStateList.valueOf(item.color)

            etAnswer.doOnTextChanged { text, start, before, count ->
                onAnswerTextChanger(item.id, text.toString())
            }
            root.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.oDragStarted(viewHolder = this@simpleDelegateAdapter);
                    onSelectedElement(item.id)
                }
                false
            }
        }
    }
}

fun answerDelegateMatch(
    onClickDelete: (String) -> Unit,
    onAnswerTextChanger: (String, Int, String) -> Unit
) = simpleDelegateAdapter<AnswerItemUi.MatchAnswer,
        AnswerItemUi,
        ViewHolderAnswerMatchBinding>(
    ViewHolderAnswerMatchBinding::inflate
) {
    binding.etAnswerFirst.doOnTextChanged { text, start, before, count ->
        onAnswerTextChanger(
            item.id,
            AnswerItemUi.MatchAnswer.FIRST_ANSWER_MATCH,
            text.toString()
        )
    }

    binding.etAnswerSecond.doOnTextChanged { text, start, before, count ->
        onAnswerTextChanger(
            item.id,
            AnswerItemUi.MatchAnswer.SECOND_ANSWER_MATCH,
            text.toString()
        )
    }

    bind {
        binding {
            etAnswerFirst.hint = context.getString(
                R.string.question_match_answer_position_hint,
                (adapterPosition + 1).toString()
            )
            if (etAnswerFirst.text.isEmpty()) {
                etAnswerFirst.setText(item.firstAnswer)
            }
            if (etAnswerSecond.text.isEmpty()) {
                etAnswerSecond.setText(item.secondAnswer)
            }
            imgDelete.setOnClickListener {
                onClickDelete(item.id)
            }
            answerFirst.backgroundTintList = ColorStateList.valueOf(item.color)
            answerSecond.backgroundTintList = ColorStateList.valueOf(item.color)
        }
    }
}

fun answerDelegateWrite(onAnswerTextChanger: (String, String) -> Unit) =
    simpleDelegateAdapter<AnswerItemUi.TextAnswer,
            AnswerItemUi,
            ViewHolderAnswerWriteBinding>(
        ViewHolderAnswerWriteBinding::inflate
    ) {
        binding {
            bind {
                etAnswer.doOnTextChanged { text, start, before, count ->
                    onAnswerTextChanger(item.id, text.toString())
                }
            }
        }
    }

fun footerPlusAddDelegate(onClickAdd: () -> Unit) =
    simpleDelegateAdapter<AnswerItemUi.FooterPlusAdd,
            AnswerItemUi,
            ViewHolderAnswerFootPlusBinding>(
        ViewHolderAnswerFootPlusBinding::inflate
    ) {
        bind {
            binding {
                imgPlus.setOnClickListener {
                    onClickAdd()
                }
            }
        }
    }