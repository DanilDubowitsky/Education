package com.testeducation.ui.base.dialog.alert

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class ViewModelHostAlertDialog<VB : ViewBinding, VM : ViewModel>(
    private val vmClass: KClass<VM>,
    onBind: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseAlertDialog<VB>(onBind) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this, viewModelFactory)[vmClass.java]
    }

}
