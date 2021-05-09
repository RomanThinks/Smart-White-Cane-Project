package com.example.cane20

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.internal_map.*

//
import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.android.synthetic.main.activity_maps.*
import java.security.Permission

abstract class InternalMapActivity : AppCompatActivity(), OnMapReadyCallback {

    var LocationPermission = false;
    var PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1
    var recentPlace = null
    var defaultLocation = LatLng(-33.8523341,151.2106085)
    val TAG = InternalMapActivity::class.java.simpleName
    val DEFAULT_ZOOM = 15

    lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.internal_map)

        Places.initialize(applicationContext, getString(R.string.google_maps_api_key)) //maps_api_key

        var placesClient = Places.createClient(this)
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fun AskForLocationPermission() { //also should be in apps permission?
            if (ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                LocationPermission = true //permission has been granted
            } else { //show that app does not have permissions....
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
            }
        }

        fun PermissionInput(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            LocationPermission = false
            when (requestCode) {
                PERMISSION_REQUEST_ACCESS_FINE_LOCATION -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        LocationPermission = true
                    }
                }
            }
            //updateLocationUI //another class
        }

        fun updateLocationUI() {
            if (map == null) { //map imported a library
                return
            }
            try {
                if (LocationPermission) {
                    mMap?.isMyLocationEnabled = true //why mMap? and not mMap
                    mMap?.uiSettings?.isMyLocationButtonEnabled = true
                } else {
                    mMap?.isMyLocationEnabled = false
                    mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    recentPlace = null
                    AskForLocationPermission()
                }
            } catch (e: SecurityException) { //what is catch, e, Security exception
                Log.e("Exception: %s", e.message, e)
            }
        }

        fun getDeviceLocation() {
            try {
                if(LocationPermission) { //should this be in the app permissions section
                    val Result = fusedLocationProviderClient.lastLocation
                    Result.addOnCompleteListener(this) {
                        task ->
                        if(task.isSuccessful) {
                            //recentPlace = task.result
                            if(recentPlace != null) {
                                /*mMap?.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(recentPlace!!.latitude, recentPlace!!.longitude),
                                        DEFAULT_ZOOM.toFloat()))*/
                            }
                        }
                        else {
                            Log.d(TAG, "Current Location is null. Using default?") //what is logging doing?
                            Log.d(TAG, "Exception: %s", task.exception)
                            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                            mMap?.uiSettings?.isMyLocationButtonEnabled = false
                        }
                    }
                }
            }
            catch(e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }
        }

        val button1 = findViewById<Button>(R.id.homebuttonIntMap) //Home button
        button1.setOnClickListener {
            val intent = Intent(
                this,
                MainActivity::class.java
            )  //when button 'settings' pressed, sent to Activity set Page
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.backbuttonIntMap) //back button
        button2.setOnClickListener {
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

        /*  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map)
        map_fragment.getMapAsync(this)
*/

        fun onMapReady(googleMap: GoogleMap) { //suppose to put a marker, it does not
            mMap = googleMap
            updateLocationUI()
            getDeviceLocation()
        }
    }
}
