package vn.linh.tracker.infrastructure

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import vn.linh.tracker.util.constant.REQUEST_CODE_LOCATION_SETTINGS

class RequestLocationProvider(val activity: Activity, val context: Context) {
    val TAG = "RequestLocationProvider"
    private var requestingLocationUpdates: Boolean = false

    @SuppressLint("MissingPermission")
    fun request(callback: LocationCallback) {
        if (requestingLocationUpdates) {
            return
        }
        requestingLocationUpdates = false
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val settingsClient = LocationServices.getSettingsClient(context)
        settingsClient.checkLocationSettings(locationSettingsRequest())
                .addOnSuccessListener(activity) {
                    fusedLocationClient.requestLocationUpdates(locationRequest(),
                            callback, Looper.myLooper())
                }
                .addOnFailureListener {
                    val statusCode = (it as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                val rae = it as ResolvableApiException
                                rae.startResolutionForResult(activity, REQUEST_CODE_LOCATION_SETTINGS)
                            } catch (sie: IntentSender.SendIntentException) {
                                // do nothing
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage = "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
    }

    private fun locationRequest(): LocationRequest {
        val locationRequest = LocationRequest()
        return locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun locationSettingsRequest(): LocationSettingsRequest {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest())
        builder.setAlwaysShow(true)
        return builder.build()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQUEST_CODE_LOCATION_SETTINGS == requestCode) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.i(TAG, "User agreed to make required location settings changes.")
//                    request(null) // todo request here
                }
                Activity.RESULT_CANCELED -> {
                    Log.i(TAG, "User chose not to make required location settings changes.")
                    requestingLocationUpdates = false
                }
            }
        }
    }
}