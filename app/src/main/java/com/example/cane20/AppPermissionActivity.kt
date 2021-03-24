package com.example.cane20

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AppPermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_permission)

        val button = findViewById<Button>(R.id.homebuttonAppPermActv) //home button
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.backbuttonAppAppPermActv) //back button
        button1.setOnClickListener{
            val intent = Intent(this, ActivitySet::class.java)
            startActivity(intent)
        }
    }
}