package com.example.currency

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currency.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()
    private var namesList: ArrayList<String>? = arrayListOf()
    private lateinit var mBinding: FragmentHomeBinding
    private var swapped: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.symbolsData.observe(viewLifecycleOwner) {
            it?.symbols?.keys?.let { it1 -> namesList?.addAll(it1) }
        }
        viewModel.ratesData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(view: View) {
        mBinding = FragmentHomeBinding.bind(view)
        mBinding.viewModel = viewModel
        mBinding.lifecycleOwner = this
        viewModel.getSymbols()
        mBinding.leftTv.setOnClickListener {
            settingCurrenciesList(true)
        }
        mBinding.rightTv.setOnClickListener {
            settingCurrenciesList(false)
        }
        mBinding.leftTv.addTextChangedListener {
            if (!TextUtils.isEmpty(mBinding.leftTv.text.toString()) && !TextUtils.isEmpty(mBinding.rightTv.text.toString())) {
                viewModel.getLatestRate(
                    mBinding.leftTv.text.toString(),
                    mBinding.rightTv.text.toString()
                )
            }
        }
        mBinding.rightTv.addTextChangedListener {
            if (!TextUtils.isEmpty(mBinding.leftTv.text.toString()) && !TextUtils.isEmpty(mBinding.rightTv.text.toString())) {
                viewModel.getLatestRate(
                    mBinding.leftTv.text.toString(),
                    mBinding.rightTv.text.toString()
                )
            }
        }

        mBinding.convertTv.setOnClickListener {
            if (!swapped) {
                mBinding.leftTv.hint = "From"
                mBinding.rightTv.hint = "To"
                swapped= true
            }else{
                swapped= false
                mBinding.leftTv.hint = "To"
                mBinding.rightTv.hint = "From"
            }
        }
    }

    private fun settingCurrenciesList(isBase: Boolean) {
        val text = if (isBase) "Base" else "Conversion"
        val builderSingle: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builderSingle.setTitle("Select $text Currency:-")
        val arrayAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_singlechoice)
        arrayAdapter.addAll(namesList?.toList()!!)
        builderSingle.setAdapter(arrayAdapter) { _, index ->
            val strName = arrayAdapter.getItem(index)

            if (isBase) {
                mBinding.leftTv.text = strName.toString()
            } else {
                mBinding.rightTv.text = strName.toString()
            }
        }
        builderSingle.show()
    }
}