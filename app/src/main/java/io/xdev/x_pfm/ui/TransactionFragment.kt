package io.xdev.x_pfm.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import io.xdev.x_pfm.R
import io.xdev.x_pfm.databinding.FragmentTransactionBinding
import io.xdev.x_pfm.repository.models.statistics.Transaction
import io.xdev.x_pfm.repository.models.statistics.TransactionType
import io.xdev.x_pfm.viewmodels.TransactionViewModel
import java.util.*

class TransactionFragment : Fragment() {
    private lateinit var binding: FragmentTransactionBinding
    private lateinit var viewModel: TransactionViewModel
    private val transactionSubmissionClickListener = { view: View ->
        val value = binding.valueBox.text.toString()
        val t: Transaction
        if (value.isEmpty() || binding.transactionTitle.text.toString().isBlank()) {
            Toast.makeText(context, getString(R.string.empty_transaction_toast), Toast.LENGTH_LONG).show()
        } else {
            t = Transaction(value.toLong(), binding.transactionTitle.text.toString(), null, viewModel.transactionType, Date())
            viewModel.submit(t)
            Navigation.findNavController(view).navigate(R.id.action_transactionFragment_to_home_fragment)
        }
    }

    private val switchedToIncome = { _: View ->
        viewModel.switchIncome()
        binding.switchIncome.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
        binding.switchIncome.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
        binding.switchExpense.setTextColor(ContextCompat.getColor(context!!, R.color.white))
        binding.switchExpense.setBackgroundColor(ContextCompat.getColor(context!!, R.color.transparent))
    }

    private val switchedToExpense = { _: View ->
        viewModel.switchExpense()
        binding.switchExpense.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
        binding.switchExpense.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
        binding.switchIncome.setTextColor(ContextCompat.getColor(context!!, R.color.white))
        binding.switchIncome.setBackgroundColor(ContextCompat.getColor(context!!, R.color.transparent))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false)
        viewModel = ViewModelProviders.of(this).get(TransactionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentManager != null)
            ViewCompat.setTranslationZ(view, fragmentManager!!.backStackEntryCount.toFloat())
        showKeyboard()
        setListeners()
    }

    private fun setListeners() {
        binding.submitButton.setOnClickListener(transactionSubmissionClickListener)
        binding.switchExpense.setOnClickListener(switchedToExpense)
        binding.switchIncome.setOnClickListener(switchedToIncome)
    }

    private fun showKeyboard() {
        if (activity != null) {
            val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }


    override fun onResume() {
        super.onResume()
        fixStatusBarColor(R.color.colorAccent)
    }

    private fun fixStatusBarColor(@ColorRes color: Int) {
        if (activity != null) {
            val window = activity?.window
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = ContextCompat.getColor(context!!, color)
        }
    }
}
