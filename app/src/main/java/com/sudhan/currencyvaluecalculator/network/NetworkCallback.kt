package com.sudhan.currencyvaluecalculator.network

interface NetworkCallback {
    fun onApiSuccess(response: Any)
    fun onApiFailure(errorMsg: String?)
}