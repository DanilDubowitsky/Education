package com.testeducation.education.app

import com.testeducation.education.di.components.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val config = AppMetricaConfig.newConfigBuilder("72576bb6-2e0b-441a-9580-f86ba8792757").build()
        AppMetrica.activate(this, config)
        return DaggerApplicationComponent.builder().application(this).build()
    }

}
