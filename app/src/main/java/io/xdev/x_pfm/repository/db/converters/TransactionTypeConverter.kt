package io.xdev.x_pfm.repository.db.converters

import androidx.room.TypeConverter
import io.xdev.x_pfm.repository.models.statistics.TransactionType
import java.util.*

class TransactionTypeConverter {
    @TypeConverter
    fun toTransactionType(type: Int?): TransactionType? {
        return when (type) {
            null -> null
            1 -> TransactionType.INCOME
            else -> TransactionType.EXPENSE
        }
    }

    @TypeConverter
    fun fromTransactionType(type: TransactionType): Int? {
        return if (type == TransactionType.INCOME) 1 else 0
    }
}