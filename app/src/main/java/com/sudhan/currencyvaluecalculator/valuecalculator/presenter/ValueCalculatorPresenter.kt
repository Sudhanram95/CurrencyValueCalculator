package com.sudhan.currencyvaluecalculator.valuecalculator.presenter

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.sudhan.currencyvaluecalculator.network.NetworkCallback
import com.sudhan.currencyvaluecalculator.valuecalculator.model.AllFlagAndCurrencyModel
import com.sudhan.currencyvaluecalculator.valuecalculator.model.BaseCurrencyValueModel
import com.sudhan.currencyvaluecalculator.valuecalculator.model.FlagAndCurrencyModel
import com.sudhan.currencyvaluecalculator.valuecalculator.repository.ValueCalculatorRepository
import com.sudhan.currencyvaluecalculator.valuecalculator.view.IValueCalculatorView
import com.sudhan.currencyvaluecalculator.valuecalculator.view.ValueCalculatorActivity
import java.io.IOException
import java.nio.charset.Charset
import java.text.DecimalFormat

class ValueCalculatorPresenter(var iValueCalculatorView: IValueCalculatorView): IValueCalculatorPresenter {

    val networkManager = ValueCalculatorRepository()

    override fun onGetContinuousCurrencyRates(baseCurrency: String, baseAmount: Double) {
        networkManager.observeValueEverySecond(baseCurrency, object : NetworkCallback {
            override fun onApiSuccess(response: Any) {
                val responseModel = response as BaseCurrencyValueModel
                Log.e("Presenter", "Response: ${Gson().toJson(responseModel)}")

                val ratesMap = LinkedHashMap<String, Double>()
                ratesMap.put(baseCurrency, baseAmount)
                ratesMap.putAll(onCalculateEquivalentValue(baseAmount, responseModel.ratesMap))
                iValueCalculatorView.onShowCurrencyValueResult(ratesMap)
            }

            override fun onApiFailure(errorMsg: String?) {
                iValueCalculatorView.onApiErrorResult()
                networkManager.stopObservingApiCall()
                Log.e("Presenter", "Error: ${errorMsg}")
            }
        })
    }

    override fun onGetFlagAndCurrencyMap(): HashMap<String, FlagAndCurrencyModel> {
        var flagAndCurrencyMap = HashMap<String, FlagAndCurrencyModel>()
        try {
            val inputStream = (iValueCalculatorView as Context).assets.open("FlagAndCurrency.json")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charset.forName("UTF-8"))
            flagAndCurrencyMap = Gson().fromJson(json, AllFlagAndCurrencyModel::class.java).map
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return flagAndCurrencyMap
    }

    override fun onCalculateEquivalentValue(baseAmount: Double, ratesMap: LinkedHashMap<String, Double>): LinkedHashMap<String, Double> {
        for (currency in ratesMap.keys) {
            val equivalentAmount = baseAmount * (ratesMap.get(currency)!!)
            ratesMap.put(currency, equivalentAmount)
        }
        return ratesMap
    }

    override fun onChangeBaseCurrency(currency: String, amount: Double) {
        (iValueCalculatorView as ValueCalculatorActivity).onUpdateView(currency, amount)
        onGetContinuousCurrencyRates(currency, amount)
    }

    override fun onDisposeApiCall() {
        networkManager.stopObservingApiCall()
    }

    override fun onHideKeyBoard() {
        iValueCalculatorView.hideKeyBoardResult()
    }

    override fun onRoundCurrencyValue(amount: Double): String {
        val decimalFormatter = DecimalFormat("###,###,###.###")
        return decimalFormatter.format(amount)
    }
}