package com.sudhan.currencyvaluecalculator.valuecalculator.view

interface IValueCalculatorView {
    fun onShowCurrencyValueResult(ratesMap: LinkedHashMap<String, Double>)
    fun onApiErrorResult()
    fun hideKeyBoardResult()
}