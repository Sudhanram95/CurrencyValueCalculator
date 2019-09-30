package com.sudhan.currencyvaluecalculator.valuecalculator.presenter

import com.sudhan.currencyvaluecalculator.valuecalculator.model.FlagAndCurrencyModel

interface IValueCalculatorPresenter {
    fun onGetContinuousCurrencyRates(baseCurrency: String, baseAmount: Double)
    fun onGetFlagAndCurrencyMap(): HashMap<String, FlagAndCurrencyModel>
    fun onCalculateEquivalentValue(baseAmount: Double, ratesMap: LinkedHashMap<String, Double>): LinkedHashMap<String, Double>
    fun onChangeBaseCurrency(currency: String, amount: Double)
    fun onDisposeApiCall()
    fun onHideKeyBoard()
    fun onRoundCurrencyValue(amount: Double): String
}