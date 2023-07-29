package com.testeducation.ui.delegates.tests.question

import android.content.res.ColorStateList
import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultBinding
import com.testeducation.ui.databinding.ViewHolderAnswerFootPlusBinding
import com.testeducation.ui.databinding.ViewHolderAnswerWriteBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun answersDelegateDefault(
    onClickCheckTrue: (Int) -> Unit,
    onClickDelete: (Int) -> Unit
) = simpleDelegateAdapter<AnswerItemUi.DefaultAnswer,
            AnswerItemUi,
            ViewHolderAnswerDefaultBinding>(
        ViewHolderAnswerDefaultBinding::inflate
    ) {
        bind {
            binding {
                etAnswer.setText(item.answerText)
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
            }
        }
    }

fun answerDelegateWrite() = simpleDelegateAdapter<AnswerItemUi.TextAnswer,
        AnswerItemUi,
        ViewHolderAnswerWriteBinding>(
    ViewHolderAnswerWriteBinding::inflate
) {}

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