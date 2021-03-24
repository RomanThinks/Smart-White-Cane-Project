package com.example.cane20

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.view.*

class VoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.voice_activity)

        val button = findViewById<Button>(R.id.homebuttonVoActv) //home button
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.backbuttonVoActv) //back button
        button2.setOnClickListener{
            val intent = Intent(this, ActivitySet::class.java)
            startActivity(intent)
        }
    }
}