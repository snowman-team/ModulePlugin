package com.xueqiu.moduleplugin.router

import android.app.Activity
import android.content.Context
import android.content.Intent

class Route {

    var pattern: String? = null
        private set

    var component: Class<out Activity>? = null

    var action: Action? = null

    constructor(pattern: String, action: Action) : super() {
        this.pattern = pattern
        this.action = action
    }

    constructor(component: Class<out Activity>) : super() {
        this.pattern = component.getAnnotation(RoutePage::class.java)?.pattern
        this.component = component
    }

    interface Action {
        fun run(ctx: Context, intent: Intent)
    }
}