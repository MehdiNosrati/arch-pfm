package io.xdev.x_pfm.repository.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.xdev.x_pfm.repository.models.statistics.Balance

@Dao
interface BalanceDao {
    @Query("SELECT * FROM balance WHERE balance_id = 0")
    suspend fun getBalance(): Balance?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBalance(balance: Balance?)
}