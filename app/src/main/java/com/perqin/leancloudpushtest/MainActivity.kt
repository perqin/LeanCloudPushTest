package com.perqin.leancloudpushtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.avos.avoscloud.AVPush
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_test.setOnClickListener {
            val avPush = AVPush()
            val obj = JSONObject("{\"alert\": \"test\"}")
            avPush.setPushToAndroid(true)
            avPush.setData(obj)
            avPush.sendInBackground()
        }
    }
}
