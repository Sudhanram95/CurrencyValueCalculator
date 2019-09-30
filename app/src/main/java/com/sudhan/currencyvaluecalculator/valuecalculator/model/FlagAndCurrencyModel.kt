package com.sudhan.currencyvaluecalculator.valuecalculator.model

import com.google.gson.annotations.SerializedName

class FlagAndCurrencyModel {
    @SerializedName("flag")
    lateinit var flag: String

    @SerializedName("currency_full_name")
    lateinit var currencyFullName: String
}