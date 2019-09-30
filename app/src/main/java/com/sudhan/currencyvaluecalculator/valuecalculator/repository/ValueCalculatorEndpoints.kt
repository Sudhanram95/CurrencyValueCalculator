package com.sudhan.currencyvaluecalculator.valuecalculator.repository

import com.sudhan.currencyvaluecalculator.valuecalculator.model.BaseCurrencyValueModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ValueCalculatorEndpoints {
    @GET("latest")
    fun getBaseCurrencyValue(@Query("base") baseCurrency: String): Observable<BaseCurrencyValueModel>
}