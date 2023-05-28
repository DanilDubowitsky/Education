package com.testeducation.ui.screen.common

import android.os.Bundle
import android.view.View
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.ui.base.dialog.alert.BaseAlertDialog
import com.testeducation.ui.databinding.DialogConfirmationBinding
import com.testeducation.ui.utils.getScreen
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.setClickListener
import javax.inject.Inject

class ConfirmationDialog : BaseAlertDialog<DialogConfirmationBinding>(
    DialogConfirmationBinding::inflate
) {

    @Inject
    lateinit var router: NavigationRouter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = this.getScreen<NavigationScreen.Common.Confirmation>()
        setupListeners()
        render(screen)
    }

    private fun render(screen: NavigationScreen.Common.Confirmation) = binding {
        confirmationText.text = screen.confirmationText
        screen.negativeBtnText?.let { btnCancel.text = it }
        screen.positiveBtnText?.let { btnConfirm.text = it }
        screen.titleText?.let { alertTitle.text = it }
    }

    private fun setupListeners() = binding {
        btnConfirm.setClickListener {
            // TODO: refactor with router
            dismiss()
            router.sendResult(NavigationScreen.Common.Confirmation.OnConfirm, Unit)
        }
        btnCancel.setClickListener(router::exit)
    }

}
