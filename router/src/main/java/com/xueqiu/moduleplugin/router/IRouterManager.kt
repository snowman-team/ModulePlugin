package com.xueqiu.moduleplugin.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

interface IRouterManager {

    fun open(ctx: Context,
             url: String,
             bundle: Bundle = Bundle(),
             intentFlag: Int? = null,
             requestCode: Int? = null): Boolean

    fun openBrowser(ctx: Context, url: String): Boolean

    fun register(route: Route)

    fun isFromRoute(intent: Intent): Boolean

    fun handleAppLink(activity: Activity, isValid: (link: String) -> Boolean): Boolean
}