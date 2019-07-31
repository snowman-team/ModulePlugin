package com.xueqiu.moduleplugin

abstract class BaseModulePlugin {

    abstract fun init()

    abstract fun getMethodProvider(): BaseMethodProvider?

}