package com.xueqiu.moduleplugin.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri

object RouterManager : IRouterManager {

    private val mRouter by lazy { Router() }

    override fun open(ctx: Context,
                      url: String,
                      bundle: Bundle,
                      intentFlag: Int?,
                      requestCode: Int?): Boolean {
        if (url.isEmpty()) {
            return false
        }
        return mRouter.open(ctx, url, intentFlag, requestCode, bundle)
    }

    override fun register(route: Route) {
        mRouter.register(route)
    }

    override fun openBrowser(ctx: Context, url: String): Boolean {
        val uri = if (url.contains("://")) {
            url.substring(url.indexOf("://") + 3).toUri()
        } else {
            url.toUri()
        }
        return try {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ctx.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun isFromRoute(intent: Intent): Boolean = mRouter.isFromRoute(intent)

    override fun handleAppLink(activity: Activity, isValid: (link: String) -> Boolean): Boolean {
        val appLink = activity.intent.data?.toString() ?: return false
        return if (isValid(appLink)) {
            open(activity, appLink)
        } else {
            openBrowser(activity, appLink)
        }
    }
}