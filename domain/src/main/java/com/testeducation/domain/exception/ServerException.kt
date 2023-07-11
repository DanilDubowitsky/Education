package com.testeducation.domain.exception

import java.io.IOException

class ServerException(
    val displayMessage: String
) : IOException()
