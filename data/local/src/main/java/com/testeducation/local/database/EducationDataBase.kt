package com.testeducation.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.testeducation.domain.database.IEducationDatabase
import com.testeducation.local.dao.question.TestPassResultDao
import com.testeducation.local.dao.question.QuestionDao
import com.testeducation.local.dao.question.TestLocalLiveTimeDao
import com.testeducation.local.dao.theme.ThemeShortDao
import com.testeducation.local.database.EducationDataBase.Companion.DATA_BASE_VERSION
import com.testeducation.local.database.converter.DataBaseTypeConverters
import com.testeducation.local.entity.answer.AnswerEntity
import com.testeducation.local.entity.question.QuestionEntity
import com.testeducation.local.entity.question.TestLocalLiveTimeEntity
import com.testeducation.local.entity.result.AnsweredQuestionEntity
import com.testeducation.local.entity.result.TestPassResultEntity
import com.testeducation.local.entity.theme.ThemeShortEntity

@Database(
    entities = [
        ThemeShortEntity::class,
        QuestionEntity::class,
        AnswerEntity::class,
        TestLocalLiveTimeEntity::class,
        AnsweredQuestionEntity::class,
        TestPassResultEntity::class
    ],
    version = DATA_BASE_VERSION,
    exportSchema = false
)
@TypeConverters(DataBaseTypeConverters::class)
abstract class EducationDataBase : RoomDatabase(), IEducationDatabase {

    override suspend fun clear() {
        clearAllTables()
    }

    abstract val themeShortDao: ThemeShortDao
    abstract val questionDao: QuestionDao
    abstract val testLocalLiveTimeDao: TestLocalLiveTimeDao
    abstract val answeredQuestionDao: TestPassResultDao

    companion object {
        const val DATA_BASE_NAME = "EDUCATION_DATABASE"
        const val DATA_BASE_VERSION = 4
    }
}