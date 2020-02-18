package io.xdev.x_pfm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Bindable
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import io.xdev.x_pfm.R
import io.xdev.x_pfm.databinding.ItemExpenseBinding
import io.xdev.x_pfm.databinding.ItemIncomeBinding
import io.xdev.x_pfm.repository.models.statistics.Transaction
import io.xdev.x_pfm.repository.models.statistics.TransactionType

class TransactionAdapter(private var transactions: List<Transaction>,
                         private val clickHandler: TransactionClickHandler) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_INCOME)
            IncomeViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                            R.layout.item_income, parent, false),
                    clickHandler)
        else
            ExpenseViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                            R.layout.item_expense, parent, false),
                    clickHandler)
    }

    fun setData(transactions: List<Transaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val t = transactions[position]
        if (t.type == TransactionType.INCOME) {
            (holder as IncomeViewHolder).bind(t)
        } else {
            (holder as ExpenseViewHolder).bind(t)
        }

    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (transactions[position].type == TransactionType.INCOME) VIEW_TYPE_INCOME else VIEW_TYPE_EXPENSE
    }

    interface TransactionClickHandler {
        fun itemClicked(position: Int)
    }

    internal inner class IncomeViewHolder(private var binding: ItemIncomeBinding,
                                          private val clickHandler: TransactionClickHandler) :
            RecyclerView.ViewHolder(binding.root),
            View.OnClickListener {


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(obj: Any) {
            binding.transaction = obj as Transaction
            binding.executePendingBindings()
        }

        override fun onClick(view: View) {
            clickHandler.itemClicked(adapterPosition)
        }
    }

    internal inner class ExpenseViewHolder(private var binding: ItemExpenseBinding,
                                          private val clickHandler: TransactionClickHandler) :
            RecyclerView.ViewHolder(binding.root),
            View.OnClickListener {


        init {
            itemView.setOnClickListener(this)
        }

        fun bind(obj: Any) {
            binding.transaction = obj as Transaction
            binding.executePendingBindings()
        }

        override fun onClick(view: View) {
            clickHandler.itemClicked(adapterPosition)
        }
    }

    companion object {
        private const val VIEW_TYPE_INCOME = 0
        private const val VIEW_TYPE_EXPENSE = 1
    }
}
