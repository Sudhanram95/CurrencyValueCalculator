package com.sudhan.currencyvaluecalculator.valuecalculatorpresenter

import com.sudhan.currencyvaluecalculator.valuecalculator.presenter.ValueCalculatorPresenter
import com.sudhan.currencyvaluecalculator.valuecalculator.view.IValueCalculatorView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class RoundCurrencyValueTest {

    lateinit var iValueCalculatorPresenter: ValueCalculatorPresenter
    var inputAmount = 0.0
    lateinit var expectedValue: String

    @Before
    fun setUp() {
        val iValueCalculatorView = Mockito.mock(IValueCalculatorView::class.java)
        iValueCalculatorPresenter = ValueCalculatorPresenter(iValueCalculatorView)
        inputAmount = 10000005.346
        expectedValue = "10,000,005.346"
    }

    @Test
    fun testRoundCurrencyValue() {
        val outputValue = iValueCalculatorPresenter.onRoundCurrencyValue(inputAmount)
        Assert.assertEquals(expectedValue, outputValue)
    }
}