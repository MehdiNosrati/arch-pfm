package io.xdev.x_pfm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.xdev.x_pfm.databinding.FragmentPreferenceBinding
import io.xdev.x_pfm.viewmodels.PreferencesViewModel

class PreferencesFragment : Fragment() {
    private lateinit var binding: FragmentPreferenceBinding
    private lateinit var viewModel: PreferencesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        this.viewModel = ViewModelProviders.of(this).get(PreferencesViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preference, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (fragmentManager != null)
            ViewCompat.setTranslationZ(view, fragmentManager!!.backStackEntryCount.toFloat())
    }

    override fun onResume() {
        super.onResume()
        fixStatusBarColor(R.color.light_gray)
        hideKeyboard()
    }

    private fun fixStatusBarColor(@ColorRes color: Int) {
        if (activity != null) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(context!!, color)
        }
    }

    private fun hideKeyboard() {
        if (activity != null) {
            val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (view != null)
                inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }
}
