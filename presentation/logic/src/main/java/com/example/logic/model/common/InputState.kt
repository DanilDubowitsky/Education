package com.example.logic.model.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class InputState : Parcelable {
    object Default : InputState()
    class Error(val errorText: String) : InputState()
    object Disabled: InputState()
}