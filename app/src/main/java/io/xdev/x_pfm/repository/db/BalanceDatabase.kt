package io.xdev.x_pfm.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.xdev.x_pfm.repository.db.daos.BalanceDao
import io.xdev.x_pfm.repository.models.statistics.Balance

@Database(entities = [Balance::class], version = 3, exportSchema = false)
abstract class BalanceDatabase : RoomDatabase() {
    abstract fun balanceDao(): BalanceDao

    companion object {
        @Volatile
        private var INSTANCE: BalanceDatabase? = null

        fun getDatabase(context: Context): BalanceDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        BalanceDatabase::class.java,
                        "balance_database"
                ).fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}