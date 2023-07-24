package com.testeducation.domain.utils

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

suspend fun startAsync(action: suspend () -> Unit): Job = withContext(coroutineContext) {
    launch(coroutineContext + Job()) { action() }
}
