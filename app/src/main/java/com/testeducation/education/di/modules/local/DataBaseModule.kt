package com.testeducation.education.di.modules.local

import android.content.Context
import androidx.room.Room
import com.testeducation.domain.database.IEducationDatabase
import com.testeducation.local.database.EducationDataBase
import com.testeducation.local.database.EducationDataBase.Companion.DATA_BASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface DataBaseModule {

    @Binds
    @Singleton
    fun bindDataBase(dataBase: EducationDataBase): IEducationDatabase

    companion object {
        @Provides
        @Singleton
        fun provideDataBase(
            context: Context
        ): EducationDataBase = Room.databaseBuilder(
            context,
            EducationDataBase::class.java,
            DATA_BASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

}