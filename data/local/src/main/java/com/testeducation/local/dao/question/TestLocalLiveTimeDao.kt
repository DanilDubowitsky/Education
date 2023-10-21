package com.testeducation.local.dao.question

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testeducation.local.entity.question.TestLocalLiveTimeEntity

@Dao
interface TestLocalLiveTimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: TestLocalLiveTimeEntity)

    @Query("SELECT lastCacheTime FROM TestLocalLiveTimeEntity WHERE testId = :testId")
    suspend fun getTestLiveTime(testId: String): kotlin.Long?
}
