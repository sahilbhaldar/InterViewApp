package com.example.interviewapp

import android.Manifest
import android.content.Context
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation

import android.location.LocationManager
import android.content.DialogInterface

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.NonNull








class HomeScreen : AppCompatActivity() {
    lateinit var txt_addr: TextView
    lateinit var txt_addr_sym: TextView

    lateinit var  ph: PreferencesHelper
    private val REQUEST_LOCATION = 1
    var locationManager: LocationManager? = null
    lateinit var latitude: String
    lateinit var longitude:kotlin.String

    lateinit var ap:AskPermission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        txt_addr = findViewById(R.id.txt_addr)
        txt_addr_sym = findViewById(R.id.txt_addr_sym)

        ph =  PreferencesHelper.getInstance(this)
        ap= AskPermission(applicationContext,this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val tempName=ph.getString(PreferencesHelper.NAME)
        txt_addr_sym.setText("Welcom $tempName\nYour current location is")
        ap.requestPermission()
        if (ap.checkPermission()) {
            if (!locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!) {
                OnGPS()
            } else {

                val handler = Handler()
                val delay = 300000 // 1000 milliseconds == 1 second
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        getLocation()
                        handler.postDelayed(this, delay.toLong())
                    }
                }, delay.toLong())

            }
        }
        else
        {
            ap.requestPermission()
        }
    }

    override fun onResume() {

        val handler = Handler()
        val delay = 1000 // 1000 milliseconds == 1 second
        handler.postDelayed(object : Runnable {
            override fun run() {
                getLocation()
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
        super.onResume()
    }
    private fun OnGPS() {
        val builder= AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val locationGPS = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat = locationGPS.latitude
                val longi = locationGPS.longitude
                latitude = lat.toString()
                longitude = longi.toString()

                val geocoder: Geocoder
                val addresses: List<Address>
                geocoder = Geocoder(this, Locale.getDefault())
                addresses = geocoder.getFromLocation(lat, longi, 1)
                val address =
                    addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName // Only if available else return NULL


                txt_addr.setText("Latitude: $latitude\nLongitude: $longitude \nCity: $city\nState: $state\nCountry: $country\nPostalcode:$postalCode\n and Address is \n $address")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.contact_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.tb_call -> {
                makeCall()
            }
            R.id.tb_mail -> {
                sendMail()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendMail() {

        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>("sahil.bhaldar09@gmail.com"))
        email.putExtra(Intent.EXTRA_SUBJECT, "Mail From app")
        email.type = "message/rfc822"

        startActivity(Intent.createChooser(email, "Choose an Email client :"))
    }

    private fun makeCall() {
        val phone_intent = Intent(Intent.ACTION_CALL)
        phone_intent.data = Uri.parse(
            "tel:"
                    + "7021660139"
        )
        startActivity(phone_intent)
    }

}