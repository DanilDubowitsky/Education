package com.testeducation.local.dao.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.testeducation.local.entity.answer.AnswerEntity
import com.testeducation.local.entity.question.QuestionEntity
import com.testeducation.local.entity.result.AnsweredQuestionEntity
import com.testeducation.local.entity.result.TestPassResultCompound
import com.testeducation.local.entity.result.TestPassResultEntity

@Dao
interface TestPassResultDao {

    @Transaction
    suspend fun insertOrUpdate(resultEntity: TestPassResultCompound) {
        val questions = resultEntity.userAnswers.map {
            it.question.question
        }
        val answers = resultEntity.userAnswers.flatMap {
            it.question.answers
        }
        val userAnswers = resultEntity.userAnswers.map {
            it.answeredQuestion
        }
        insertResult(resultEntity.passResultEntity)
        insertQuestions(questions)
        insertAnswers(answers)
        insertUserAnswers(userAnswers)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(resultEntity: TestPassResultEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(question: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAnswers(answers: List<AnsweredQuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswers(answers: List<AnswerEntity>)

    @Query("SELECT * FROM TestPassResultEntity WHERE testId = :testId")
    suspend fun getTestPassResult(testId: String): TestPassResultCompound

}
