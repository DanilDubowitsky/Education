package com.example.ui.screen.common

import android.os.Bundle
import android.view.View
import com.example.navigation.screen.NavigationScreen
import com.example.ui.base.dialog.bottom.BaseBottomSheetDialog
import com.example.ui.databinding.DialogInformationBinding
import com.example.ui.utils.FragmentUtils.getScreen
import com.example.ui.utils.FragmentUtils.invoke
import com.example.ui.utils.ViewUtils.setClickListener

class InformationDialog : BaseBottomSheetDialog<DialogInformationBinding>(
    DialogInformationBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screen = this.getScreen<NavigationScreen.Common.Information>()
        render(screen)
        setupListeners()
    }

    private fun render(screen: NavigationScreen.Common.Information) = binding {
        informationText.text = screen.description
        screen.btnText?.let { btnClose.text = it }
        screen.titleText?.let { informationTitle.text = it }
    }

    private fun setupListeners() = binding {
        btnClose.setClickListener(::dismiss)
    }

}
