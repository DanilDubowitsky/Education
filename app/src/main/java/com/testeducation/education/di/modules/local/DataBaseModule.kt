package com.testeducation.education.di.modules.local

import android.content.Context
import androidx.room.Room
import com.testeducation.local.database.EducationDataBase
import com.testeducation.local.database.EducationDataBase.Companion.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        context: Context
    ): EducationDataBase = Room.databaseBuilder(
        context,
        EducationDataBase::class.java,
        DATA_BASE_NAME
    ).build()

}