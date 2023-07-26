package com.testeducation.ui.delegates.tests.question

import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultBinding
import com.testeducation.ui.databinding.ViewHolderAnswerFootPlusBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun answersDelegateDefault() =
    simpleDelegateAdapter<AnswerItemUi.DefaultAnswer,
            AnswerItemUi,
            ViewHolderAnswerDefaultBinding>(
        ViewHolderAnswerDefaultBinding::inflate
    ) {
        bind {
            binding {
                etAnswer.setText(item.answerText)
            }
        }
    }

fun footerPlusAddDelegate() =
    simpleDelegateAdapter<AnswerItemUi.FooterPlusAdd,
            AnswerItemUi,
            ViewHolderAnswerFootPlusBinding>(
        ViewHolderAnswerFootPlusBinding::inflate
    ) {
        bind {
            binding {

            }
        }
    }