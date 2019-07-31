package com.xueqiu.moduleplugin.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xueqiu.moduleplugin.ModulePluginManager
import com.xueqiu.moduleplugin.router.RouterManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RouterManager.handleAppLink(this) { appLink ->
            return@handleAppLink appLink.startsWith("xueqiu") && !appLink.contains("https")
        }

        ModulePluginManager.init(AppModulePlugin())
        val provider = ModulePluginManager.getMethodProvider<IAppMethodProvider>(AppModulePlugin.MODULE_PLUGIN_APP)
        provider?.test()

        btn_test.setOnClickListener {
            // RouterManager.open(this, "/test")
            RouterManager.open(this, "/test/manual$")
        }
    }
}
