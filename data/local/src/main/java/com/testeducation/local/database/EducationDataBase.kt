package com.testeducation.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.testeducation.local.dao.theme.ThemeShortDao
import com.testeducation.local.database.EducationDataBase.Companion.DATA_BASE_VERSION
import com.testeducation.local.entity.theme.ThemeShortEntity

@Database(
    entities = [
        ThemeShortEntity::class
    ], version = DATA_BASE_VERSION
)
abstract class EducationDataBase : RoomDatabase() {

    abstract val themeShortDao: ThemeShortDao

    companion object {
        const val DATA_BASE_NAME = "EDUCATION_DATABASE"
        const val DATA_BASE_VERSION = 1
    }
}