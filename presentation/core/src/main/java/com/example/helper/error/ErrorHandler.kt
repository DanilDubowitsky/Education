package com.example.helper.error

import com.example.domain.exception.ServerException
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen

class ErrorHandler(
    private val router: NavigationRouter
) : IErrorHandler {

    override fun handleError(throwable: Throwable) {
        val message = if (throwable is ServerException) {
            throwable.displayMessage
        } else throwable.stackTraceToString()
        val screen = NavigationScreen.Common.Information(message)
        router.navigateTo(screen)
    }
}
