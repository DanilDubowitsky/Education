package com.testeducation.ui.screen.common

import android.os.Bundle
import android.view.View
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.ui.base.dialog.bottom.BaseBottomSheetDialog
import com.testeducation.ui.databinding.DialogInformationBinding
import com.testeducation.ui.utils.getScreen
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.setClickListener

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
