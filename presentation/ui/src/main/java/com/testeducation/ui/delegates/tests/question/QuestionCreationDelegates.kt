package com.testeducation.ui.delegates.tests.question

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.MotionEvent
import com.testeducation.logic.model.test.AnswerCreationUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultBinding
import com.testeducation.ui.databinding.ViewHolderAnswerFootPlusBinding
import com.testeducation.ui.databinding.ViewHolderAnswerMatchBinding
import com.testeducation.ui.databinding.ViewHolderAnswerOrderBinding
import com.testeducation.ui.databinding.ViewHolderAnswerWriteBinding
import com.testeducation.ui.listener.drag.IDragStartListener
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter


fun answersDelegateDefault(
    onClickCheckTrue: (String) -> Unit,
    onClickDelete: (String) -> Unit,
    onAnswerTextChanger: (String) -> Unit
) = simpleDelegateAdapter<AnswerCreationUI.DefaultAnswer,
        AnswerCreationUI,
        ViewHolderAnswerDefaultBinding>(
    ViewHolderAnswerDefaultBinding::inflate
) {
    bind {
        binding {
            etAnswer.text = item.answerText
            imgDelete.setOnClickListener {
                onClickDelete(item.id)
            }
            imgCheckIcon.setOnClickListener {
                onClickCheckTrue(item.id)
            }

            imgCheckIcon.backgroundTintList =
                ColorStateList.valueOf(item.resource.isTrueColor)

            root.backgroundTintList = ColorStateList.valueOf(item.color)

            etAnswer.setOnClickListener {
                onAnswerTextChanger(item.id)
            }
        }
    }
}

fun answerDelegateMatch(
    onClickDelete: (String) -> Unit,
    onAnswerTextChanger: (String, Boolean) -> Unit
) = simpleDelegateAdapter<AnswerCreationUI.MatchAnswer,
        AnswerCreationUI,
        ViewHolderAnswerMatchBinding>(
    ViewHolderAnswerMatchBinding::inflate
) {
    binding.etAnswerFirst.setOnClickListener {
        onAnswerTextChanger(
            item.id,
            true
        )
    }

    binding.etAnswerSecond.setOnClickListener {
        onAnswerTextChanger(
            item.id,
            false
        )
    }

    bind {
        binding {
            etAnswerFirst.hint = context.getString(
                R.string.question_match_answer_position_hint,
                (adapterPosition + 1).toString()
            )
            etAnswerFirst.text = item.firstAnswer
            etAnswerSecond.text = item.secondAnswer
            imgDelete.setOnClickListener {
                onClickDelete(item.id)
            }
            answerFirst.backgroundTintList = ColorStateList.valueOf(item.color)
            answerSecond.backgroundTintList = ColorStateList.valueOf(item.color)
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
fun answerDelegateOrder(
    onAnswerTextChanger: (String) -> Unit,
    mDragStartListener: IDragStartListener,
    onSelectedElement: (String) -> Unit,
    onClickDelete: (String) -> Unit
) = simpleDelegateAdapter<AnswerCreationUI.OrderAnswer,
            AnswerCreationUI,
            ViewHolderAnswerOrderBinding>(
        ViewHolderAnswerOrderBinding::inflate
    ) {
        binding {
            etAnswer.setOnClickListener {
                onAnswerTextChanger(item.id)
            }
            imgMove.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.oDragStarted(viewHolder = this@simpleDelegateAdapter);
                    onSelectedElement(item.id)
                }
                false
            }
            imgDelete.setOnClickListener {
                onClickDelete(item.id)
            }
            bind {
                etAnswer.text = item.answerText
                containerAnswer.backgroundTintList = ColorStateList.valueOf(item.color)
                tvNumber.text = item.order.toString()
                constraintOrder.backgroundTintList = ColorStateList.valueOf(item.color)
            }
        }
    }

fun answerDelegateWrite(onAnswerTextChanger: (String) -> Unit) =
    simpleDelegateAdapter<AnswerCreationUI.TextAnswer,
            AnswerCreationUI,
            ViewHolderAnswerWriteBinding>(
        ViewHolderAnswerWriteBinding::inflate
    ) {
        binding {
            bind {
                etAnswer.text = item.text
                etAnswer.setOnClickListener {
                    onAnswerTextChanger(item.id)
                }
            }
        }
    }

fun footerPlusAddDelegate(
    onClickAdd: () -> Unit
) = simpleDelegateAdapter<AnswerCreationUI.FooterPlusAdd,
            AnswerCreationUI,
            ViewHolderAnswerFootPlusBinding>(
        ViewHolderAnswerFootPlusBinding::inflate
    ) {
        bind {
            binding {
                imgPlus.setClickListener {
                    onClickAdd()
                }
            }
        }
    }