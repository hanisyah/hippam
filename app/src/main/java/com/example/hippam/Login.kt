package com.example.hippam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        button = findViewById(R.id.btnLogin)
        button.setOnClickListener{
            startActivity(Intent(this, Home::class.java))
        }
    }
}