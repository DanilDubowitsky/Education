package com.testeducation.helper.error

import com.testeducation.domain.exception.ServerException
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen

class ExceptionHandler(
    private val router: NavigationRouter
) : IExceptionHandler {

    override fun handleError(throwable: Throwable) {
        val message = if (throwable is ServerException) {
            throwable.displayMessage
        } else throwable.stackTraceToString()
        val screen = NavigationScreen.Common.Information(message)
        router.navigateTo(screen)
    }
}
