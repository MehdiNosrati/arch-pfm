package io.xdev.x_pfm.repository

import io.xdev.x_pfm.repository.db.daos.BalanceDao
import io.xdev.x_pfm.repository.db.daos.TransactionDao
import io.xdev.x_pfm.repository.models.entities.Tag
import io.xdev.x_pfm.repository.models.statistics.Balance
import io.xdev.x_pfm.repository.models.statistics.Transaction
import io.xdev.x_pfm.repository.models.statistics.TransactionType
import java.util.*

class TransactionRepository constructor(private val transactionDao: TransactionDao, private val balanceDao: BalanceDao) {
    var balance: Balance? = Balance.getInitialBalance()
    var tags: MutableList<Tag>? = null

    init {
        tags = ArrayList()
    }

    suspend fun submitTransaction(t: Transaction) {
        transactionDao.insertTransaction(t)
        applyBalanceUpdates(t)
        balanceDao.updateBalance(balance)
    }

    private fun applyBalanceUpdates(t: Transaction) {
        when (t.type) {
            TransactionType.INCOME -> {
                balance?.apply {
                    totalIncome += t.value
                    balance += t.value
                }
            }
            TransactionType.EXPENSE -> {
                balance?.apply {
                    totalExpense += t.value
                    balance -= t.value
                }
            }
        }
    }

    suspend fun transactions(): List<Transaction> {
        return transactionDao.getTransactions()
    }

    suspend fun retrieveBalance(): Balance? {
        balance = balanceDao.getBalance()
        return balance
    }
}
