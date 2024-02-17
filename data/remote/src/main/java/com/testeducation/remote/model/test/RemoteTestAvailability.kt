package com.testeducation.remote.model.test

/**
 * @property Public - доступен всем;
 * @property ViaLinkAll - доступен только по ссылке или коду на него для всех, кто имеет ссылку или код
 */
enum class RemoteTestAvailability {
    Public,
    ViaLinkAll
}