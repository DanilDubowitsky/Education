package com.testeducation.screen.tests.edit.publish

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.edit.TestPublishSideEffect
import com.testeducation.logic.screen.tests.edit.TestPublishState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import java.util.Calendar
import java.util.TimeZone

class TestPublishViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestPublishModelState, TestPublishState>,
    val isPublish: Boolean,
    private val router: NavigationRouter
) : BaseViewModel<TestPublishModelState, TestPublishState, TestPublishSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestPublishModelState = TestPublishModelState()

    init {
        init()
    }

    fun save() = intent {
        val modelState = getModelState()
        val calendar = modelState.calendar
        calendar.add(Calendar.HOUR, -TimeZone.getDefault().rawOffset / 3600000)

        val status = when (modelState.status) {
            TestPublishModelState.StatusPublish.PUBLISH -> NavigationScreen.Tests.TestPublish.OnTestPublish.Result.StatusPublish.PUBLISH
            TestPublishModelState.StatusPublish.SCHEDULED -> NavigationScreen.Tests.TestPublish.OnTestPublish.Result.StatusPublish.SCHEDULED
            else -> NavigationScreen.Tests.TestPublish.OnTestPublish.Result.StatusPublish.DRAFT
        }
        router.sendResult(NavigationScreen.Tests.TestPublish.OnTestPublish, NavigationScreen.Tests.TestPublish.OnTestPublish.Result(
            statusPublish = status,
            time = if (modelState.status == TestPublishModelState.StatusPublish.SCHEDULED) calendar.timeInMillis else null
        ))
        router.exit()
    }

    fun changeStatus(status: TestPublishState.StatusPublish) = intent {
        val statusSelected = when (status) {
            TestPublishState.StatusPublish.PUBLISH -> TestPublishModelState.StatusPublish.PUBLISH
            TestPublishState.StatusPublish.SCHEDULED -> TestPublishModelState.StatusPublish.SCHEDULED
            else -> TestPublishModelState.StatusPublish.DRAFT
        }
        updateModelState {
            copy(
                status = statusSelected
            )
        }
    }

    private fun init() = intent {
        val status = if (isPublish) {
            TestPublishModelState.StatusPublish.PUBLISH
        } else {
            TestPublishModelState.StatusPublish.DRAFT
        }
        updateModelState {
            copy(
                status = status
            )
        }
    }

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        intent {
            val modelState = getModelState()
            val calendarNew = Calendar.getInstance()
            val calendarLast = modelState.calendar
            calendarNew.set(Calendar.DATE, dayOfMonth)
            calendarNew.set(Calendar.MONTH, month)
            calendarNew.set(Calendar.YEAR, year)
            calendarNew.set(Calendar.MINUTE, calendarLast.get(Calendar.MINUTE))
            calendarNew.set(Calendar.HOUR, calendarLast.get(Calendar.HOUR))
            updateModelState {
                copy(
                    calendar = calendarNew
                )
            }
        }
    }

    fun openTimeDateDialog() {
        intent {
            postSideEffect(TestPublishSideEffect.OpenDatePicker(getModelState().calendar))
        }
    }

    fun openTimeDialog() {
        intent {
            postSideEffect(TestPublishSideEffect.OpenTimePicker(getModelState().calendar))
        }
    }

    fun setTime(hourOfDay: Int, minute: Int) {
        intent {
            val modelState = getModelState()
            val calendarNew = Calendar.getInstance()
            val calendarLast = modelState.calendar
            calendarNew.set(Calendar.DATE, calendarLast.get(Calendar.DATE))
            calendarNew.set(Calendar.MONTH, calendarLast.get(Calendar.MONTH))
            calendarNew.set(Calendar.YEAR, calendarLast.get(Calendar.YEAR))
            calendarNew.set(Calendar.MINUTE, minute)
            calendarNew.set(Calendar.HOUR, hourOfDay)
            updateModelState {
                copy(
                    calendar = calendarNew
                )
            }
        }
    }
}