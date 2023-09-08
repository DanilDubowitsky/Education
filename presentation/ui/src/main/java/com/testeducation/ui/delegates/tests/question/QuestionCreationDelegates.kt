package com.testeducation.ui.delegates.tests.question

import android.content.res.ColorStateList
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultBinding
import com.testeducation.ui.databinding.ViewHolderAnswerFootPlusBinding
import com.testeducation.ui.databinding.ViewHolderAnswerMatchBinding
import com.testeducation.ui.databinding.ViewHolderAnswerWriteBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun answersDelegateDefault(
    onClickCheckTrue: (Int) -> Unit,
    onClickDelete: (Int) -> Unit,
    onAnswerTextChanger: (Int, String) -> Unit
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
            if (item.isTrue) {
                R.color.colorGray_1
            } else {
                R.color.colorWhite
            }.also { color ->
                imgCheckIcon.backgroundTintList =
                    ColorStateList.valueOf(context.getColor(color))
            }
            root.backgroundTintList = ColorStateList.valueOf(item.color)

            etAnswer.doOnTextChanged { text, start, before, count ->
                onAnswerTextChanger(item.id, text.toString())
            }
        }
    }
}

fun answerDelegateMatch(
    onClickDelete: (Int) -> Unit,
    onAnswerTextChanger: (Int, Int, String) -> Unit
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
                (absoluteAdapterPosition + 1).toString()
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

fun answerDelegateWrite(onAnswerTextChanger: (Int, String) -> Unit) =
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