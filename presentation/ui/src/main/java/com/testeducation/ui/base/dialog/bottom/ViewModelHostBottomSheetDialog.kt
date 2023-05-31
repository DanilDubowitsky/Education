package com.testeducation.ui.base.dialog.bottom

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class ViewModelHostBottomSheetDialog<VB : ViewBinding, VM : ViewModel>(
    private val vmClass: KClass<VM>,
    onBind: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseBottomSheetDialog<VB>(onBind) {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, viewModelFactory)[vmClass.java]
    }

}
