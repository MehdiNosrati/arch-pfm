package io.xdev.x_pfm.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.xdev.x_pfm.repository.TransactionRepository
import io.xdev.x_pfm.repository.db.BalanceDatabase
import io.xdev.x_pfm.repository.db.TransactionDatabase
import io.xdev.x_pfm.repository.models.statistics.Balance
import io.xdev.x_pfm.repository.models.statistics.Transaction
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionRepository: TransactionRepository
    var transactions: MutableLiveData<List<Transaction>> = MutableLiveData()
    var balance: MutableLiveData<Balance> = MutableLiveData()

    init {
        val transactionDao = TransactionDatabase.getDatabase(application).transactionDao()
        val balanceDao = BalanceDatabase.getDatabase(application).balanceDao()
        transactionRepository = TransactionRepository(transactionDao, balanceDao)
    }

    fun transactions() {
        viewModelScope.launch {
            transactions.value = transactionRepository.transactions()
        }
    }

    fun balance() {
        viewModelScope.launch {
            balance.value = transactionRepository.retrieveBalance()
        }
    }

}
