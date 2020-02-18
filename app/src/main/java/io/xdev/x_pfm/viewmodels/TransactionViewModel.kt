package io.xdev.x_pfm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.xdev.x_pfm.repository.TransactionRepository
import io.xdev.x_pfm.repository.db.BalanceDatabase
import io.xdev.x_pfm.repository.db.TransactionDatabase
import io.xdev.x_pfm.repository.models.entities.Tag
import io.xdev.x_pfm.repository.models.statistics.Transaction
import io.xdev.x_pfm.repository.models.statistics.TransactionType
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionRepository: TransactionRepository
    var transactionType: TransactionType = TransactionType.INCOME



    init {
        val transactionDao = TransactionDatabase.getDatabase(application).transactionDao()
        val balanceDao = BalanceDatabase.getDatabase(application).balanceDao()
        transactionRepository = TransactionRepository(transactionDao, balanceDao)
    }

    val tags: MutableList<Tag>
        get() = transactionRepository.tags!!

    fun submit(t: Transaction) = viewModelScope.launch {
        transactionRepository.submitTransaction(t)
    }

    fun switchIncome() {
        transactionType = TransactionType.INCOME
    }

    fun switchExpense() {
        transactionType = TransactionType.EXPENSE
    }

}
