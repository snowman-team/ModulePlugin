package com.xueqiu.moduleplugin.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xueqiu.moduleplugin.router.RoutePage

@RoutePage("^/test$")
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test)

    }
}