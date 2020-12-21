package com.shubhankarpandey_developer.news

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed

class MainActivity : AppCompatActivity() {
    private val splashTim = 3000.00
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed( {
            startActivity(Intent(this,PdfActivity::class.java))
            finish()
        }, splashTim.toLong())
    }
}