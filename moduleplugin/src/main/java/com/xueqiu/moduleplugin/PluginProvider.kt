package com.xueqiu.moduleplugin

internal class PluginProvider {

    private val mPlugins = HashMap<String, BaseModulePlugin>()

    @Throws(Throwable::class)
    fun load(plugin: BaseModulePlugin) {
        val pluginName = plugin.javaClass.getAnnotation(ModulePlugin::class.java)?.name
        if (pluginName.isNullOrEmpty()) {
            return
        }
        if (mPlugins.containsKey(pluginName)) {
            return
        }
        plugin.init()
        mPlugins[pluginName] = plugin
    }

    fun <T : BaseMethodProvider> getMethodProvider(pluginName: String): T? {
        return mPlugins[pluginName]?.getMethodProvider() as T?
    }

}