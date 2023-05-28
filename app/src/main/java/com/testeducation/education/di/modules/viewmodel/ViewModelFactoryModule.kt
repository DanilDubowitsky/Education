package com.testeducation.education.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Provider

@Module
object ViewModelFactoryModule {

    @Provides
    @Reusable
    fun provideViewModelFactory(
        viewModels: MutableMap<Class<out ViewModel>,
                @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModels[modelClass]?.get() as T
            }
        }
    }

}
