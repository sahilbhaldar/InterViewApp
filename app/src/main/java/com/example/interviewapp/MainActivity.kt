package com.example.interviewapp

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var edtName: TextInputEditText
    lateinit var edtMobileNo: TextInputEditText
    lateinit var btn_save: Button
    lateinit var ph: PreferencesHelper
    lateinit var ap: AskPermission
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.edt_name)
        edtMobileNo = findViewById(R.id.edt_mobi_no)
        btn_save = findViewById(R.id.btn_save)
        ph =  PreferencesHelper.getInstance(this)
        ap = AskPermission(applicationContext,this)
        ap!!.requestPermission()
        btn_save.setOnClickListener {
            if (validateInput()) {
                val tempName = edtName.getText().toString().trim { it <= ' ' }
                val tempMobileNo = edtMobileNo.getText().toString().trim { it <= ' ' }
                ph.setBoolean(PreferencesHelper.ISLOGIN, true)
                ph.setString(PreferencesHelper.NAME, tempName)
                ph.setString(PreferencesHelper.NUMBER, tempMobileNo)

                    openHomeScreen()

            }
        }
    }

    private fun validateInput(): Boolean {
        val tempName = edtName!!.text.toString().trim { it <= ' ' }
        val tempMobileNo = edtMobileNo!!.text.toString().trim { it <= ' ' }
        return if (tempName.isEmpty()) {
            edtName!!.error = getString(R.string.name_err)
            false
        } else if (tempMobileNo.length != 10) {
            edtMobileNo!!.error = getString(R.string.mobile_no_err)
            false
        } else {
            edtName!!.error = null
            edtMobileNo!!.error = null
            true
        }
    }

    private fun openHomeScreen() {
        val send = Intent(applicationContext, HomeScreen::class.java)
        startActivity(send)
        finish()
    }
}