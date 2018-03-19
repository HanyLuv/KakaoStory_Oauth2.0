package com.work.hany.kakaoproject

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toolbar
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by hany on 2018. 3. 11..
 */

abstract class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    override fun onResume() {
        super.onResume()
        if (findViewById<Toolbar>(R.id.tool_bar) != null) {
            setSupportActionBar(tool_bar)
        }
    }

}