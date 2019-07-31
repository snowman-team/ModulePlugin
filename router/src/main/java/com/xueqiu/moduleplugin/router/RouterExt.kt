package com.xueqiu.moduleplugin.router

import android.util.Log
import java.net.URLDecoder
import java.util.regex.Pattern

fun getPathString(url: String): String {
    var path = ""
    try {
        val queryIndex = url.indexOf("?")
        path = if (queryIndex > 0) {
            url.substring(0, queryIndex)
        } else {
            url
        }
    } catch (e: Exception) {
    }
    return path
}

fun splitQuery(url: String): Map<String, String> {
    val queryPairs = LinkedHashMap<String, String>()
    try {
        val queryIndex = url.indexOf("?")
        if (queryIndex < 0) {
            return queryPairs
        }
        val query = url.substring(queryIndex + 1)
        val pairs = query.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (pair in pairs) {
            val idx = pair.indexOf("=")
            queryPairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
                URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
        }
    } catch (e: Exception) {
        Log.w("Utils", "", e)
    }
    return queryPairs
}

fun compileRegex(url: String): Pattern? {
    var pattern: Pattern? = null
    try {
        pattern = Pattern.compile(url)
    } catch (e: Exception) {
        Log.e("route", "", e)
    }

    return pattern
}
