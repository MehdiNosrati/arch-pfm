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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import io.xdev.x_pfm.R
import io.xdev.x_pfm.databinding.FragmentHomeBinding
import io.xdev.x_pfm.ui.adapters.TransactionAdapter
import io.xdev.x_pfm.viewmodels.HomeViewModel

class HomeFragment : Fragment(), TransactionAdapter.TransactionClickHandler {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private val addTransactionNavigationClickListener = { v: View ->
        Navigation.findNavController(v)
                .navigate(R.id.action_home_fragment_to_transactionFragment)
    }
    private val preferencesNavigationClickListener = { v: View ->
        Navigation.findNavController(v)
                .navigate(R.id.action_home_fragment_to_preference_fragment)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTranslationZ(view, 0f)
        setListeners()
        setupTransactionList()
    }

    private fun setListeners() {
        binding.addTransactionButton.setOnClickListener(addTransactionNavigationClickListener)
        binding.preferencesButton.setOnClickListener(preferencesNavigationClickListener)
    }

    private fun setupTransactionList() {
        binding.transactionList.layoutManager = LinearLayoutManager(context)
        binding.transactionList.setHasFixedSize(true)
        val adapter = TransactionAdapter(mutableListOf(), this)
        binding.transactionList.adapter = adapter
        viewModel.transactions()
        viewModel.balance()
        viewModel.transactions.observe(this, Observer {
            adapter.setData(it)
        })
        viewModel.balance.observe(this, Observer {
            if (it != null) {
                binding.balanceStatistic.text = it.balance.toString()
                binding.incomeStatistic.text = it.totalIncome.toString()
                binding.expenseStatistic.text = it.totalExpense.toString()
            } else {
                binding.balanceStatistic.text = 0.toString()
                binding.incomeStatistic.text = 0.toString()
                binding.expenseStatistic.text = 0.toString()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
        viewModel.balance()
        fixStatusBarColor(R.color.colorPrimary)
    }

    private fun fixStatusBarColor(@ColorRes color: Int) {
        if (activity != null) {
            val window = activity?.window
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = ContextCompat.getColor(context!!, color)
        }
    }

    private fun hideKeyboard() {
        if (activity != null) {
            val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (view != null)
                inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    override fun itemClicked(position: Int) {
        Toast.makeText(activity, position.toString() + "", Toast.LENGTH_LONG).show()
    }
}
