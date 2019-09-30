package com.sudhan.currencyvaluecalculator.valuecalculator.model

import com.google.gson.annotations.SerializedName

class AllFlagAndCurrencyModel {
    @SerializedName("currency_and_flag")
    lateinit var map: HashMap<String, FlagAndCurrencyModel>
}