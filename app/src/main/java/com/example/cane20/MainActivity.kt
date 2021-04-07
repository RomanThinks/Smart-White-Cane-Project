package com.example.cane20

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("google.navigation:")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
            /*val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)*/
        }

        val button2 = findViewById<Button>(R.id.button2) //Home button
        button2.setOnClickListener{
            val intent = Intent(this, ActivitySet::class.java)  //when button 'settings' pressed, sent to Activity set Page
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            val intent = Intent(this, InternalMapActivity::class.java) //Internal Map activity
            startActivity(intent)
        }

    }
}