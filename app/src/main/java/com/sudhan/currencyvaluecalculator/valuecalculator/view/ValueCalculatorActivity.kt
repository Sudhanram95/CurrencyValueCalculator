package com.sudhan.currencyvaluecalculator.valuecalculator.view

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sudhan.currencyvaluecalculator.R
import com.sudhan.currencyvaluecalculator.valuecalculator.presenter.IValueCalculatorPresenter
import com.sudhan.currencyvaluecalculator.valuecalculator.presenter.ValueCalculatorPresenter

class ValueCalculatorActivity : AppCompatActivity(), IValueCalculatorView {

    lateinit var rvRates: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var llError: LinearLayout
    lateinit var btnRetry: Button
    lateinit var iValueCalculatorPresenter: IValueCalculatorPresenter
    var currencyAdapter: CurrencyAdapter? = null
    var baseCurrency = "EUR"
    var baseAmount = 100.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_calculator)

        rvRates = findViewById(R.id.rv_currency_rates)
        progressBar = findViewById(R.id.progree_bar)
        llError = findViewById(R.id.ll_error)
        btnRetry = findViewById(R.id.btn_retry)

        btnRetry.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            llError.visibility = View.GONE
            onResume()
        }

        iValueCalculatorPresenter = ValueCalculatorPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        iValueCalculatorPresenter.onGetContinuousCurrencyRates(baseCurrency, baseAmount)
    }

    override fun onPause() {
        super.onPause()
        iValueCalculatorPresenter.onDisposeApiCall()
    }

    override fun onShowCurrencyValueResult(ratesMap: LinkedHashMap<String, Double>) {
        progressBar.visibility = View.GONE
        rvRates.visibility = View.VISIBLE
        if(currencyAdapter == null) {
            currencyAdapter = CurrencyAdapter(iValueCalculatorPresenter, ratesMap)
            rvRates.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rvRates.adapter = currencyAdapter
        } else{
            currencyAdapter!!.updateCurrencyValue(ratesMap)
        }
    }

    fun onUpdateView(currency: String, amount: Double) {
        progressBar.visibility = View.VISIBLE
        baseCurrency = currency
        baseAmount = amount
        val linearLayoutManager = rvRates.layoutManager as LinearLayoutManager
        linearLayoutManager.scrollToPositionWithOffset(0, 0)
    }

    override fun onApiErrorResult() {
        progressBar.visibility = View.GONE
        rvRates.visibility = View.GONE
        llError.visibility = View.VISIBLE
    }

    override fun hideKeyBoardResult() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow((this as Activity).currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
