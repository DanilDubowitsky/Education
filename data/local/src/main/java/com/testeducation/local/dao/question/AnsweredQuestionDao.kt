package com.testeducation.local.dao.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testeducation.local.entity.question.AnsweredQuestionEntity
import com.testeducation.local.entity.question.AnsweredQuestionWithAnswers

@Dao
interface AnsweredQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: List<AnsweredQuestionEntity>)

    @Query("SELECT * FROM AnsweredQuestionEntity WHERE testId = :testId")
    suspend fun getAnsweredQuestions(
        testId: String
    ): List<AnsweredQuestionWithAnswers>

}
