package com.example.cane20

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SensorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sensor_activity)

        val button = findViewById<Button>(R.id.button) //Home button
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.button1) //back button
        button1.setOnClickListener{
            val intent = Intent(this, ActivitySet::class.java)
            startActivity(intent)
        }

        //establish if bluetooth device connected first (Just the Cane)
        //Retrieves parameters from the user and computes them to a value
        //the value is a threshold for the super sonic sensor that will be
        //modified in the Pi of the Smart White Cane.
    }
}