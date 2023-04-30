package com.example.local.config

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.core.config.IConfigSource
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

class ConfigSource(
    private val sharedPreferences: SharedPreferences
) : IConfigSource {

    private val lock: ReadWriteLock = ReentrantReadWriteLock()

    private fun <T> ReadWriteLock.readSafety(invoke: () -> T): T {
        this.readLock().tryLock()
        val value = invoke()
        this.readLock().unlock()
        return value
    }

    private fun ReadWriteLock.writeSafety(invoke: () -> Unit) {
        this.writeLock().tryLock()
        invoke()
        this.writeLock().unlock()
    }

    override fun getString(key: String, defaultValue: String): String {
        return lock.readSafety {
            sharedPreferences.getString(key, defaultValue).orEmpty()
        }
    }

    override fun setString(key: String, value: String) {
        lock.writeSafety {
            sharedPreferences.edit {
                putString(key, value)
            }
        }
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return lock.readSafety {
            sharedPreferences.getInt(key, defaultValue)
        }
    }

    override fun setInt(key: String, value: Int) {
        lock.writeSafety {
            sharedPreferences.edit {
                putInt(key, value)
            }
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return lock.readSafety {
            sharedPreferences.getBoolean(key, defaultValue)
        }
    }

    override fun setBoolean(key: String, value: Boolean) {
        lock.writeSafety {
            sharedPreferences.edit {
                putBoolean(key, value)
            }
        }
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return lock.readSafety {
            sharedPreferences.getFloat(key, defaultValue)
        }
    }

    override fun setFloat(key: String, value: Float) {
        lock.writeSafety {
            sharedPreferences.edit {
                putFloat(key, value)
            }
        }
    }

}
