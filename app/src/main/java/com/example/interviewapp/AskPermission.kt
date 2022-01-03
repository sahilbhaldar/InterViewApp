package com.example.interviewapp

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat

class AskPermission(var mContext: Context, var mActivity: Activity) :
    OnRequestPermissionsResultCallback {
    fun checkPermission(): Boolean {
        val FirstPermissionResult =
            ContextCompat.checkSelfPermission(mContext, permission.ACCESS_COARSE_LOCATION)
        val SecondPermissionResult =
            ContextCompat.checkSelfPermission(mContext, permission.ACCESS_FINE_LOCATION)
        val ThirdPermissionResult =
            ContextCompat.checkSelfPermission(mContext, permission.CALL_PHONE)
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED && SecondPermissionResult == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {

        ActivityCompat.requestPermissions(
            mActivity, arrayOf(
                permission.ACCESS_COARSE_LOCATION,
                permission.ACCESS_FINE_LOCATION,
                permission.CALL_PHONE
            ), RequestPermissionCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            RequestPermissionCode -> if (grantResults.size > 0) {
                val coarseLocationPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val fineLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED
                val callPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED
                if (coarseLocationPermission && fineLocationPermission ) {
                    Log.w("Permission", "Permission Granted")
                } else {
                    Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val RequestPermissionCode = 1
    }
}
