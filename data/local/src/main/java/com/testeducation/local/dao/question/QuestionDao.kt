package com.testeducation.local.dao.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.testeducation.local.entity.answer.AnswerEntity
import com.testeducation.local.entity.question.QuestionEntity
import com.testeducation.local.entity.question.QuestionWithAnswers

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(question: QuestionEntity)

    @Transaction
    @Query("SELECT * FROM QuestionEntity WHERE testId = :testId")
    suspend fun getQuestions(testId: String): List<QuestionWithAnswers>

    @Transaction
    suspend fun insertOrUpdate(question: QuestionWithAnswers) {
        insertOrUpdate(question.question)
        insertOrUpdateAnswers(question.answers)
    }

    @Transaction
    suspend fun insertOrUpdate(questions: List<QuestionWithAnswers>) {
        questions.forEach { question ->
            insertOrUpdate(question)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAnswers(questions: List<AnswerEntity>)

    @Transaction
    suspend fun removeAndAddQuestions(testId: String, questions: List<QuestionWithAnswers>) {
        removeAnswer(questions.map { it.question.id })
        removeQuestions(testId)
        insertOrUpdate(questions)
    }

    @Query("DELETE FROM QuestionEntity WHERE testId = :testId")
    suspend fun removeQuestions(testId: String)

    @Query("DELETE FROM AnswerEntity WHERE questionId in (:questionList)")
    suspend fun removeAnswer(questionList: List<String>)

    @Query("SELECT EXISTS (SELECT 1 FROM QuestionEntity WHERE testId = :testId)")
    suspend fun hasEntries(testId: String): Boolean

}
