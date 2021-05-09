package com.example.cane20

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class ActivitySet : AppCompatActivity() {

    //BluetoothAdapter bluetoothAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device not capable of supporting Bluetooth
        }

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            val REQUEST_ENABLE_BT = 1;
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        val button = findViewById<Button>(R.id.button3) //Home button
        button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button1 = findViewById<Button>(R.id.button4) //Bluetooth button
        button1.setOnClickListener{
            val intent = Intent(this, BluetoothActivity::class.java) //substituted for the bluetooth activity
            startActivity(intent)
        }

        val button2 = findViewById<Button>(R.id.button5) //Sensor Calibration button
        button2.setOnClickListener{
            val intent = Intent(this, SensorActivity::class.java)
            startActivity(intent)
        }

        val button3 = findViewById<Button>(R.id.button6)
        button3.setOnClickListener{
            val intent = Intent(this, VoiceActivity::class.java)
            startActivity(intent)
        }

        val button4 = findViewById<Button>(R.id.button7)
        button4.setOnClickListener{
            val intent = Intent(this, VoiceCommandActivity::class.java)
            startActivity(intent)
        }

        val button5 = findViewById<Button>(R.id.button8)
        button5.setOnClickListener{
            val intent = Intent(this, MoreActivity::class.java) //is now 'about', kept name 'more'
            startActivity(intent)
        }

        val button6 = findViewById<Button>(R.id.button9)
        button6.setOnClickListener{
            val intent = Intent(this, AppPermissionActivity::class.java)
            startActivity(intent)
        }
    }
}