package com.testeducation.ui.view.custom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.WidgetCardTestBinding
import com.testeducation.ui.utils.hideView
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showView

class CardTest @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = WidgetCardTestBinding.inflate(LayoutInflater.from(context), this, true)

    fun setContent(testShortUI: TestShortUI.Test) {
        binding {
            themeText.text = testShortUI.theme.title
            likesCountText.text = testShortUI.likes.toString()
            passesCountText.text = testShortUI.passesCount.toString()
            titleText.text = testShortUI.title
            questionsCountText.text =
                context.resources.getQuantityString(
                    R.plurals.questions_count_plurals,
                    testShortUI.questionsCount,
                    testShortUI.questionsCount
                )
            root.setCardBackgroundColor(Color.parseColor(testShortUI.color))
            setStyle(testShortUI.style)
            if (testShortUI.liked) likesIcon.setImageResource(R.drawable.ic_fav_filled)
            else likesIcon.setImageResource(R.drawable.ic_test_favorite)
        }
    }

    fun setClickListener(listener: () -> Unit) {
        binding.root.setClickListener(listener)
    }

    fun setOnLikeClickListener(listener: () -> Unit) {
        binding.likesIcon.setClickListener {
            listener()
        }
    }

    fun setStyle(style: CardTestStyle) {
        binding {
            cardDots.hideView()
            cardCircle.hideView()
            cardX.hideView()
            ellipsesLayout.hideView()
            fiveEllipse.hideView()
            cardStar.hideView()
            cardStarLittle.hideView()
            cardCircleLittle.hideView()
            cardArrow.hideView()

            when (style) {
                CardTestStyle.X -> cardX.showView()
                CardTestStyle.O -> {
                    cardCircle.showView()
                    cardCircleLittle.showView()
                }
                CardTestStyle.DOTTED -> cardDots.showView()
                CardTestStyle.ELLIPSE -> {
                    ellipsesLayout.showView()
                    fiveEllipse.showView()
                }
                CardTestStyle.STAR -> {
                    cardStar.showView()
                    cardStarLittle.showView()
                }
                CardTestStyle.ARROW -> {
                    cardArrow.showView()
                }
            }
        }
    }
}