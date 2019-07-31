package com.xueqiu.moduleplugin.app

import android.util.Log

class AppMethodProvider : IAppMethodProvider {

    override fun test() {
        Log.e("method provider", "test")
    }

}