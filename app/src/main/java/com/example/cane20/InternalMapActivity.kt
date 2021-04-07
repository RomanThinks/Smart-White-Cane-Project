package com.example.cane20

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*

class InternalMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.internal_map)

        val button1 = findViewById<Button>(R.id.homebuttonIntMap) //Home button
        button1.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)  //when button 'settings' pressed, sent to Activity set Page
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.backbuttonIntMap) //back button
        button2.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        /*val button3 = findViewById<Button>(R.id.launch)
        button3.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=22.659239, 88.435534&m=w")) //destination coordinates
            intent.setPackage("com.google.android.apps.maps") //opens maps the other app
            startActivity(intent)
        }*/ //Another now unused method

        lateinit var mMap: GoogleMap

        fun onMapReady(googleMap: GoogleMap) { //suppose to put a marker, it does not
            mMap = googleMap
            val sydney = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions().position(sydney).title("Sydney Marker"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }

    }
}