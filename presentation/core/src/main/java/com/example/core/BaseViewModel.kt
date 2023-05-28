package com.example.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helper.error.IExceptionHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.coroutineContext

abstract class BaseViewModel<MODEL_STATE : Any, UI_STATE : Any, SIDE_EFFECT : Any>(
    protected val reducer: IReducer<MODEL_STATE, UI_STATE>,
    protected val errorHandler: IExceptionHandler
) : ViewModel(), ContainerHost<UI_STATE, SIDE_EFFECT> {

    val stateFlow
        get() = container.stateFlow
    val sideEffectFlow
        get() = container.sideEffectFlow

    @Volatile
    private lateinit var modelState: MODEL_STATE

    abstract val initialModelState: MODEL_STATE

    private val mutex: Mutex = Mutex()

    private val jobMap: ConcurrentHashMap<String, Job> = ConcurrentHashMap()

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

    protected suspend fun launchSingleJob(
        key: String,
        onError: ((Throwable) -> Unit)? = null,
        action: suspend CoroutineScope.() -> Unit
    ): Job {
        jobMap.remove(key)?.cancel()
        return launchJob(onError, action)
    }

    protected suspend fun launchJob(
        onError: ((Throwable) -> Unit)? = null,
        action: suspend CoroutineScope.() -> Unit
    ): Job {
        val launchingContext = if (onError != null) {
            container.settings.intentLaunchingDispatcher +
                    CoroutineExceptionHandler { coroutineContext, throwable ->
                        onError(throwable)
                    }
        } else container.settings.intentLaunchingDispatcher + coroutineContext
        return viewModelScope.launch(
            context = launchingContext,
            block = action
        )
    }

}
