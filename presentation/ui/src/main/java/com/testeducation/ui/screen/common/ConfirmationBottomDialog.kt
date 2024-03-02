package com.testeducation.ui.screen.common

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.ui.base.dialog.bottom.BaseBottomSheetDialog
import com.testeducation.ui.databinding.DialogConfirmationBottomBinding
import com.testeducation.ui.utils.getScreen
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.setClickListener
import javax.inject.Inject

class ConfirmationBottomDialog : BaseBottomSheetDialog<DialogConfirmationBottomBinding>(
    DialogConfirmationBottomBinding::inflate
) {

    @Inject
    lateinit var router: NavigationRouter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = this.getScreen<NavigationScreen.Common.ConfirmationBottom>()
        render(screen)
        setupListeners()
    }

    private fun render(screen: NavigationScreen.Common.ConfirmationBottom) = binding {
        title.text = screen.title
        description.text = screen.description
        setButtonContent(screen.buttonRight, btnRight)
        setButtonContent(screen.buttonLeft, btnLeft)
    }

    private fun setupListeners() = binding {
        btnRight.setClickListener {
            router.exit()
            router.sendResult(NavigationScreen.Common.ConfirmationBottom.ButtonRight, Unit)
        }
        btnLeft.setClickListener {
            router.exit()
            router.sendResult(NavigationScreen.Common.ConfirmationBottom.ButtonLeft, Unit)
        }
    }

    private fun setButtonContent(
        screen: NavigationScreen.Common.ConfirmationBottom.Button,
        buttonView: Button
    ) {
        buttonView.text = screen.text
        buttonView.backgroundTintList = ColorStateList.valueOf(screen.color)
    }

}
