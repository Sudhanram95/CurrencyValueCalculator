package com.sudhan.currencyvaluecalculator.valuecalculator.repository

import android.util.Log
import com.sudhan.currencyvaluecalculator.network.NetworkCallback
import com.sudhan.currencyvaluecalculator.network.NetworkUtil
import com.sudhan.currencyvaluecalculator.valuecalculator.model.BaseCurrencyValueModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ValueCalculatorRepository {
    val apiEndpoint = NetworkUtil.retrofitHelper()?.create(ValueCalculatorEndpoints::class.java)
    lateinit var baseCurrency: String
    lateinit var networkCallback: NetworkCallback
    var compositeDisposable = CompositeDisposable()

    fun observeValueEverySecond(baseCurrency: String, networkCallback: NetworkCallback) {
        this.baseCurrency = baseCurrency
        this.networkCallback = networkCallback

        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }

        compositeDisposable.add(Observable.interval(1000, 5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getOtherCurrencyEquivalentValue, this::catchObservingError))
    }

    fun getOtherCurrencyEquivalentValue(aLong: Long) {
        apiEndpoint!!.getBaseCurrencyValue(baseCurrency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<BaseCurrencyValueModel>() {
                override fun onNext(currencyConverterModel: BaseCurrencyValueModel) {
                    networkCallback.onApiSuccess(currencyConverterModel)
                }

                override fun onError(e: Throwable) {
                    networkCallback.onApiFailure(e.message)
                }

                override fun onComplete() {

                }
            })
    }

    fun catchObservingError(error: Throwable) {
        Log.e("Mananger", "Error: ${error.message}")
    }

    fun stopObservingApiCall() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}