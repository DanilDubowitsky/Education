package com.testeducation.ui.delegates.tests.question

import android.content.Context
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderQuestionTypeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadDrawable
import com.testeducation.ui.utils.simpleDelegateAdapter

fun selectionQuestionTypeDelegate(selectionOnClick: (QuestionTypeUiItem) -> Unit) =
    simpleDelegateAdapter<QuestionTypeUiItem,
            QuestionTypeUiItem,
            ViewHolderQuestionTypeBinding>(
        ViewHolderQuestionTypeBinding::inflate
    ) {
        bind {
            binding {
                when (item.type) {
                    QuestionTypeUi.DEFAULT -> {
                        setResource(
                            title = R.string.question_type_answer,
                            icon = R.drawable.ic_answer_choosing,
                            context
                        )
                    }

                    QuestionTypeUi.ORDER -> {
                        setResource(
                            title = R.string.question_type_order,
                            icon = R.drawable.ic_answer_order,
                            context
                        )
                    }

                    QuestionTypeUi.MATCH -> {
                        setResource(
                            title = R.string.question_type_match,
                            icon = R.drawable.ic_answer_match,
                            context
                        )

                    }

                    QuestionTypeUi.WRITE_ANSWER -> {
                        setResource(
                            title = R.string.question_type_write_answer,
                            icon = R.drawable.ic_answer_write,
                            context
                        )
                    }
                }
                root.setOnClickListener {
                    selectionOnClick(item)
                }
            }
        }
    }

private fun ViewHolderQuestionTypeBinding.setResource(title: Int, icon: Int, context: Context) {
    tvTitle.text = context.getString(title)
    imgIcon.background = context.loadDrawable(icon)
}