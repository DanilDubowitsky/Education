package com.example.ui.screen.common

import android.os.Bundle
import android.view.View
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import com.example.ui.base.dialog.alert.BaseAlertDialog
import com.example.ui.databinding.DialogConfirmationBinding
import com.example.ui.utils.FragmentUtils.getScreen
import com.example.ui.utils.ViewUtils.setClickListener
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

    private fun render(screen: NavigationScreen.Common.Confirmation) = with(binding) {
        confirmationText.text = screen.confirmationText
        screen.negativeBtnText?.let { btnCancel.text = it }
        screen.positiveBtnText?.let { btnConfirm.text = it }
        screen.titleText?.let { alertTitle.text = it }
    }

    private fun setupListeners() = with(binding) {
        btnConfirm.setClickListener {
            // TODO: refactor with router
            dismiss()
            router.sendResult(NavigationScreen.Common.Confirmation.OnConfirm, Unit)
        }
        btnCancel.setClickListener(router::exit)
    }

}
