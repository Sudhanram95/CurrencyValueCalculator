package com.sudhan.currencyvaluecalculator

import androidx.test.rule.ActivityTestRule
import com.sudhan.currencyvaluecalculator.valuecalculator.presenter.ValueCalculatorPresenter
import com.sudhan.currencyvaluecalculator.valuecalculator.view.ValueCalculatorActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetFlagAndCurrencyMapTest {

    lateinit var iValueCalculatorPresenter: ValueCalculatorPresenter
    lateinit var expectedCurrencyName: String
    lateinit var outputCurrencyName: String

    @get:Rule
    var activityRule = ActivityTestRule<ValueCalculatorActivity>(ValueCalculatorActivity::class.java)

    @Before
    fun setUp() {
        val iValueCalculatorView = activityRule.activity
        iValueCalculatorPresenter = ValueCalculatorPresenter(iValueCalculatorView)
        expectedCurrencyName = "Indian Rupee"
    }

    @Test
    fun testOnGetFlagAndCurrencyMap() {
        val testFlagAndCurrencyMap = iValueCalculatorPresenter.onGetFlagAndCurrencyMap()
        outputCurrencyName = testFlagAndCurrencyMap.get("INR")!!.currencyFullName
        Assert.assertEquals(expectedCurrencyName, outputCurrencyName)
    }
}