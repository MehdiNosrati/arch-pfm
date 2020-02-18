package io.xdev.x_pfm.repository.models.statistics

import io.xdev.x_pfm.repository.models.entities.Tag
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class TransactionTest {
    @Test
    fun operate_TransactionOperationWorks() {
        val income = Income(0, "t1", null, Tag(), Date())
        assertEquals(Balance.instance.balance, income.operate())
    }

    @Test
    fun operate_TransactionAddition() {
        val income = Income(10, "t2", null, Tag(), Date())
        val value = income.operate()
        assertEquals(Balance.instance.balance, value)
    }

    @Test
    fun operate_TransactionSubtraction() {
        val expense = Expense(-10, "t3", null, Tag(), Date())
        val value = expense.operate()
        assertEquals(Balance.instance.balance, value)
    }
}