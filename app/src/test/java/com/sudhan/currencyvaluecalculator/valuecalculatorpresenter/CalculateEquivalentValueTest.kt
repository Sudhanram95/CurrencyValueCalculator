package com.sudhan.currencyvaluecalculator.valuecalculatorpresenter

import com.google.gson.Gson
import com.sudhan.currencyvaluecalculator.util.TestUtil
import com.sudhan.currencyvaluecalculator.valuecalculator.model.BaseCurrencyValueModel
import com.sudhan.currencyvaluecalculator.valuecalculator.presenter.ValueCalculatorPresenter
import com.sudhan.currencyvaluecalculator.valuecalculator.view.IValueCalculatorView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.collections.LinkedHashMap

class CalculateEquivalentValueTest {

    lateinit var iValueCalculatorPresenter: ValueCalculatorPresenter
    lateinit var testBaseCurrencyValueModel: BaseCurrencyValueModel
    var inputAmount = 0.0
    var expectedAmount: Double = 0.0
    lateinit var testRatesMap: LinkedHashMap<String, Double>

    @Before
    fun setUp() {
        val inputStream = CalculateEquivalentValueTest::class.java.classLoader!!.getResourceAsStream("GetRatesResponse.json")
        val testResponse = TestUtil.convertStreamToString(inputStream)
        val iValueCalculatorView = mock(IValueCalculatorView::class.java)
        iValueCalculatorPresenter = ValueCalculatorPresenter(iValueCalculatorView)
        testBaseCurrencyValueModel = Gson().fromJson(testResponse, BaseCurrencyValueModel::class.java)
        inputAmount = 100.00
        testRatesMap = testBaseCurrencyValueModel.ratesMap
        expectedAmount = 8363.3
    }

    @Test
    fun testOnCalculateEquivalentValue() {
        testRatesMap = iValueCalculatorPresenter.onCalculateEquivalentValue(inputAmount, testRatesMap)
        val outputAmount = testRatesMap.get("INR")!!
        Assert.assertEquals(expectedAmount, outputAmount, 0.0)
    }
}