package com.example.cane20

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class VoiceCommandActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.voice_command_activity)

        val button = findViewById<Button>(R.id.homebuttonVcActv) //home button
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.backbuttonVcActv) //back button
        button1.setOnClickListener{
            val intent = Intent(this, ActivitySet::class.java)
            startActivity(intent)
        }
    }
}