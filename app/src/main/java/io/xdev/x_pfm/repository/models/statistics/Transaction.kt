package io.xdev.x_pfm.repository.models.statistics

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "transactions")
data class Transaction constructor(
        @ColumnInfo(name = "transaction_value") val value: Long,
        @ColumnInfo(name = "transaction_title") @Bindable val title: String,
        @ColumnInfo(name = "transaction_description") val description: String?,
        @ColumnInfo(name = "transaction_type") val type: TransactionType,
        @ColumnInfo(name = "transaction_date") val date: Date?) : BaseObservable() {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
    val valueText: String
        @Bindable
        get() = value.toString()

    @Bindable
    fun getDateString(): String {
        return SimpleDateFormat("EEEE', 'MMMM d", Locale.ENGLISH).format(date!!)
    }
}
enum class TransactionType {
    INCOME, EXPENSE
}
