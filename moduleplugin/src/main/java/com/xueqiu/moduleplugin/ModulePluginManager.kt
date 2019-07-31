package com.xueqiu.moduleplugin

object ModulePluginManager {

    private val mPluginProvider by lazy { PluginProvider() }

    fun init(plugin: BaseModulePlugin) = mPluginProvider.load(plugin)

    fun <T : BaseMethodProvider> getMethodProvider(pluginName: String) = mPluginProvider.getMethodProvider<T>(pluginName)

}