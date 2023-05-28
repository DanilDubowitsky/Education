package com.example.ui.base.dialog.bottom

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.ui.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseBottomSheetDialog<VB : ViewBinding>(
    private val onBind: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BottomSheetDialogFragment(), HasAndroidInjector {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    @Inject
    protected lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    protected var isFullScreen = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener(::onDialogShow)
        }
    }

    protected open fun onDialogShow(dialogInterface: DialogInterface) {
//        dialogInterface as BottomSheetDialog
//        dialogInterface.behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = onBind(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
