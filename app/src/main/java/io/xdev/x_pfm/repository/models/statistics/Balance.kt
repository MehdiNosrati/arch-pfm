package io.xdev.x_pfm.repository.models.statistics

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balance")
data class Balance constructor(
        @ColumnInfo(name = "current_balance") @Bindable var balance: Long,
        @ColumnInfo(name = "total_income") @Bindable var totalIncome: Long,
        @ColumnInfo(name = "total_expense") @Bindable var totalExpense: Long) : BaseObservable() {
    @ColumnInfo(name = "balance_id")
    @PrimaryKey
    var id = 0

    companion object {
        private var initial: Balance? = null

        fun getInitialBalance(): Balance {
            if (initial == null) {
                initial = Balance(0, 0, 0)
            }
            return initial!!
        }
    }
}