package io.xdev.x_pfm.repository

import io.xdev.x_pfm.repository.models.entities.Tag
import io.xdev.x_pfm.repository.models.statistics.Expense
import io.xdev.x_pfm.repository.models.statistics.Income
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class TransactionRepositoryTest {
    @Test
    fun repository_works() {
        val repository = TransactionRepository.instance
        val income = Income(100, "income", null, Tag(), Date())
        val expense = Expense(50, "expense", null, Tag(), Date())
        repository.submitTransaction(income)
        repository.submitTransaction(expense)
        assertEquals(repository.getBalance(), 50)
        assertEquals(repository.transactions.size.toLong(), 2)

    }

    @Test
    fun repository_totalBalance() {
        val repository = TransactionRepository.instance
        val i1 = Income(10, "t1", null, Tag(), Date())
        val i2 = Income(20, "t2", null, Tag(), Date())
        val i3 = Income(30, "t3", null, Tag(), Date())
        val i4 = Income(40, "t4", null, Tag(), Date())
        val i5 = Expense(50, "t5", null, Tag(), Date())
        repository.submitTransactions(i1, i2, i3, i4, i5)
        assertEquals(repository.totalIncome, 100)
        assertEquals(repository.transactions.size.toLong(), 5)
        assertEquals(repository.totalExpense, 50)
    }
}