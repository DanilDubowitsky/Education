package com.testeducation.ui.base.dialog.alert

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.testeducation.ui.R
import dagger.android.support.DaggerDialogFragment

abstract class BaseAlertDialog<VB : ViewBinding>(
    private val onBind: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : DaggerDialogFragment() {

    protected var isTransparentBackground = false

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.window?.setBackgroundDrawableResource(
                R.drawable.background_white_rounded_corners_20
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = R.style.WideDialog

        setStyle(STYLE_NO_TITLE, style)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = onBind(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            if (isTransparentBackground) setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
