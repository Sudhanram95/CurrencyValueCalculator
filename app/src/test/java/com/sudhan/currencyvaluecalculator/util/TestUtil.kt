package com.sudhan.currencyvaluecalculator.util

import java.io.InputStream
import java.util.*

object TestUtil {

    fun convertStreamToString(inputStream: InputStream): String {
        val scanner = Scanner(inputStream).useDelimiter("//A")
        var str = ""
        if (scanner.hasNext())
            str = scanner.next()
        return str
    }
}