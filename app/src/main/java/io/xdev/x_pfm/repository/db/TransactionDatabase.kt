package io.xdev.x_pfm.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.xdev.x_pfm.repository.db.converters.DateConverter
import io.xdev.x_pfm.repository.db.converters.TransactionTypeConverter
import io.xdev.x_pfm.repository.db.daos.TransactionDao
import io.xdev.x_pfm.repository.models.statistics.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, TransactionTypeConverter::class)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionDatabase::class.java,
                        "transaction_database"
                ).fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}