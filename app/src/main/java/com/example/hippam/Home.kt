package com.example.hippam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Home : AppCompatActivity() {
    private lateinit var imageIn: ImageView
    private lateinit var textIn: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        imageIn = findViewById(R.id.imgInput)
        imageIn.setOnClickListener{
            startActivity(Intent(this, Scan::class.java))
        }
        textIn = findViewById(R.id.txtInput)
        textIn.setOnClickListener{
            startActivity(Intent(this, Scan::class.java))
        }
    }
}