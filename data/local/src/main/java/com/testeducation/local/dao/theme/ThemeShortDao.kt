package com.testeducation.local.dao.theme

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.testeducation.local.entity.theme.ThemeShortEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeShortDao {

    @Query("SELECT * FROM ThemeShortEntity")
    fun getThemesShortReactive(): Flow<List<ThemeShortEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateShortThemes(themes: List<ThemeShortEntity>)

}
