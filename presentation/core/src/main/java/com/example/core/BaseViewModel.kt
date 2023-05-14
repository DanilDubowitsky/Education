package com.example.core

import androidx.lifecycle.ViewModel
import com.example.helper.error.IErrorHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseViewModel<MODEL_STATE : Any, UI_STATE : Any, SIDE_EFFECT : Any>(
    protected val reducer: IReducer<MODEL_STATE, UI_STATE>,
    protected val errorHandler: IErrorHandler
) : ViewModel(), ContainerHost<UI_STATE, SIDE_EFFECT> {

    val stateFlow
        get() = container.stateFlow
    val sideEffectFlow
        get() = container.sideEffectFlow

    @Volatile
    private lateinit var modelState: MODEL_STATE

    abstract val initialModelState: MODEL_STATE

    private val mutex: Mutex = Mutex()

    override val container: Container<UI_STATE, SIDE_EFFECT> by lazy {
        container(
            reducer.reduce(initialModelState),
            Container.Settings(
                exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                    handleThrowable(throwable)
                })
        ) {
            modelState = initialModelState
        }
    }

    protected open fun handleThrowable(throwable: Throwable) {
        errorHandler.handleError(throwable)
    }

    protected suspend fun getModelState(): MODEL_STATE {
        return mutex.withLock {
            modelState
        }
    }

    protected suspend fun SimpleSyntax<UI_STATE, SIDE_EFFECT>.updateModelState(
        state: MODEL_STATE.() -> MODEL_STATE
    ) = mutex.withLock {
        val newModelState = state(modelState)
        if (newModelState != modelState) {
            modelState = newModelState
            reduce {
                reducer.reduce(modelState)
            }
        }
    }

}
