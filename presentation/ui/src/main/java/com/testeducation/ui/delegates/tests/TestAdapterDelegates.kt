package com.testeducation.ui.delegates.tests

import android.graphics.Color
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderTestShortBinding
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.ui.utils.hideView
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.showView
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createTestShortAdapterDelegate() = simpleDelegateAdapter<TestShortUI,
        TestShortUI,
        ViewHolderTestShortBinding>(
    ViewHolderTestShortBinding::inflate
) {
    bind {
        binding {
            themeText.text = item.theme.title
            likesCountText.text = item.likes.toString()
            passesCountText.text = item.passesCount.toString()
            titleText.text = item.title
            questionsCountText.text =
                context.resources.getQuantityString(
                    R.plurals.questions_count_plurals,
                    item.questionsCount,
                    item.questionsCount
                )
            root.setCardBackgroundColor(Color.parseColor(item.color))
            bindStyle(item.style)
        }
    }
}

private fun ViewHolderTestShortBinding.bindStyle(style: TestShortUI.Style) {
    cardDots.hideView()
    cardCircle.hideView()
    cardX.hideView()
    ellipsesLayout.hideView()
    fiveEllipse.hideView()

    when (style) {
        TestShortUI.Style.X -> cardX.showView()
        TestShortUI.Style.O -> cardCircle.showView()
        TestShortUI.Style.DOTTED -> cardDots.showView()
        TestShortUI.Style.ELLIPSE -> {
            ellipsesLayout.showView()
            fiveEllipse.showView()
        }
    }
}
