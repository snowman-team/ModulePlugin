package com.xueqiu.moduleplugin.app

import android.content.Context
import android.content.Intent
import android.util.Log
import com.xueqiu.moduleplugin.BaseMethodProvider
import com.xueqiu.moduleplugin.BaseModulePlugin
import com.xueqiu.moduleplugin.ModulePlugin
import com.xueqiu.moduleplugin.router.Route
import com.xueqiu.moduleplugin.router.RouterManager

@ModulePlugin(name = AppModulePlugin.MODULE_PLUGIN_APP)
class AppModulePlugin : BaseModulePlugin() {

    companion object {
        const val MODULE_PLUGIN_APP = "app"
    }

    override fun init() {
        Log.e("module plugin", "test")
        RouterManager.register(Route("^/test/manual$", object : Route.Action {
            override fun run(ctx: Context, intent: Intent) {
                intent.putExtra("test key", "test value")
                ctx.startActivity(intent)
            }
        }))
        RouterManager.register(Route(TestActivity::class.java))
    }

    override fun getMethodProvider(): BaseMethodProvider? = AppMethodProvider()

}