package com.testeducation.local.dao.theme

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.testeducation.local.entity.theme.ThemeShortEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeShortDao {

    @Query("SELECT * FROM ThemeShortEntity")
    fun getThemesShortReactive(): Flow<List<ThemeShortEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateShortThemes(themes: List<ThemeShortEntity>)

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM ThemeShortEntity) THEN 1 ELSE 0 END")
    suspend fun hasEntries(): Boolean

    @Query("DELETE FROM ThemeShortEntity")
    suspend fun clearTable()

    @Transaction
    suspend fun clearTableAndSetData(
        themes: List<ThemeShortEntity>
    ) {
        clearTable()
        insertOrUpdateShortThemes(themes)
    }

}
