package com.sudhan.currencyvaluecalculator.valuecalculator.model

import com.google.gson.annotations.SerializedName

class BaseCurrencyValueModel {
    @SerializedName("base")
    lateinit var baseCurrency: String

    @SerializedName("date")
    lateinit var date: String

    @SerializedName("rates")
    lateinit var ratesMap: LinkedHashMap<String, Double>
}