package io.xdev.x_pfm.repository.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.xdev.x_pfm.repository.models.statistics.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(t: Transaction)

    @Query("SELECT * FROM transactions ORDER BY transaction_date DESC")
    suspend fun getTransactions(): List<Transaction>
}