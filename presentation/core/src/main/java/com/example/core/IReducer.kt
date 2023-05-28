package com.example.core

interface IReducer<MODEL_STATE : Any, UI_STATE : Any> {

    fun reduce(modelState: MODEL_STATE): UI_STATE
}
