package com.testeducation.remote.handler

import com.testeducation.remote.ITokenExpirationListener

class TokenExpirationListener : ITokenExpirationListener {

    private var onTokenExpired: (() -> Unit)? = null

    override fun onTokenExpired() {
        onTokenExpired?.invoke()
    }

    override fun setOnTokenExpired(listener: () -> Unit) {
        this.onTokenExpired = listener
    }

}
