package com.testeducation.ui.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class ViewModelHostActivity<VM : ViewModel, VB : ViewBinding>(
    private val viewModelClass: KClass<VM>,
    private val onBind: (LayoutInflater) -> VB
) : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: VM

    protected lateinit var viewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = onBind(layoutInflater)
        viewModel = ViewModelProvider(this, viewModelFactory)[viewModelClass.java]
        setContentView(viewBinding.root)
    }

}
