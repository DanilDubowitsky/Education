package com.example.core.config

interface IConfigSource {
    fun getString(key: String, defaultValue: String = ""): String
    fun setString(key: String, value: String)
    fun getInt(key: String, defaultValue: Int = Int.MAX_VALUE): Int
    fun setInt(key: String, value: Int)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun setBoolean(key: String, value: Boolean)
    fun getFloat(key: String, defaultValue: Float = Float.MAX_VALUE): Float
    fun setFloat(key: String, value: Float)
    fun setLong(key: String, value: Long)
    fun getLong(key: String): Long

    interface Provider {
        fun provideConfigSourceInstance(name: String?): IConfigSource
        fun provideEncryptedConfigSourceInstance(name: String): IConfigSource
    }
}
