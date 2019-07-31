package com.xueqiu.moduleplugin.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

internal class Router {

    companion object {

        const val KEY_URL = "key_url"
        const val KEY_FULL_URL = "key_full_url"
        const val ACTION_ROUTE = "action_route"
    }

    private val mRouteTables: MutableList<Route> = LinkedList()

    fun register(routeTable: Route) {
        mRouteTables.add(routeTable)
    }

    private fun match(url: String,
                      routes: MutableList<Route> = mRouteTables,
                      existParams: MutableMap<String, String> = HashMap()): Pair<Route, MutableMap<String, String>?>? {
        var pathString = getPathString(url)
        if (pathString.contains("://")) {
            pathString = pathString.substring(pathString.indexOf("://") + 2)
        }
        existParams[KEY_URL] = url
        routes.forEach { route ->
            route.pattern?.let {
                val matcher = Pattern.compile(it).matcher(pathString)
                if (matcher.find()) {
                    existParams.putAll(splitQuery(url))
                    return Pair(route, existParams)
                }
            }

        }
        return null
    }

    fun open(ctx: Context,
             url: String,
             intentFlag: Int? = null,
             requestCode: Int? = null,
             bundle: Bundle = Bundle()): Boolean {
        val routeResult = match(url) ?: return false
        val route = routeResult.first

        if (route.component != null) {
            val intent = Intent(ctx, route.component)
            routeResult.second?.forEach {
                bundle.putString(it.key, it.value)
            }
            intent.putExtras(bundle)
            if (intentFlag != null) {
                intent.flags = intentFlag
            }
            intent.putExtra(KEY_FULL_URL, url)
            intent.action = ACTION_ROUTE
            if (requestCode != null && ctx is Activity) {
                ctx.startActivityForResult(intent, requestCode)
            } else {
                ctx.startActivity(intent)
            }

            return true
        }

        if (route.action != null) {
            val intent = Intent()
            routeResult.second?.forEach {
                intent.putExtra(it.key, it.value)
            }
            if (intentFlag != null) {
                intent.flags = intentFlag
            }
            intent.putExtra(KEY_FULL_URL, url)
            intent.action = ACTION_ROUTE
            route.action?.run(ctx, intent)
            return true
        }

        return false
    }

    fun isFromRoute(intent: Intent): Boolean {
        return ACTION_ROUTE == intent.action
    }

}