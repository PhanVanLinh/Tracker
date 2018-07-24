package vn.linh.tracker.infrastructure

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import vn.linh.tracker.util.constant.REQUEST_CODE_FITNESS_PERMISSION
import vn.linh.tracker.util.constant.REQUEST_CODE_GOOGLE_SIGN_IN
import java.util.concurrent.TimeUnit


class StepSensorProvider(var fragment: Fragment, var context: Context) {
    val TAG = "StepFragment"
    private var listener: OnDataPointListener? = null

    fun start() {
        if (!isSignedIn()) {
            signIn()
            return
        }
        if (!hasOAuthPermission()) {
            requestOAuthPermission()
            return
        }
        findFitnessDataSources()
    }

    private fun getSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .build()
    }

    private fun isSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }

    private fun signIn() {
        val signInClient = GoogleSignIn.getClient(context, getSignInOptions())
        val signInIntent = signInClient.signInIntent
        fragment.startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
    }

    private fun getFitnessSignInOptions(): FitnessOptions {
        return FitnessOptions.builder().addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE).build()
    }

    private fun hasOAuthPermission(): Boolean {
        val fitnessOptions = getFitnessSignInOptions()
        return GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(context),
                fitnessOptions)
    }

    private fun requestOAuthPermission() {
        val fitnessOptions = getFitnessSignInOptions()
        GoogleSignIn.requestPermissions(fragment, REQUEST_CODE_FITNESS_PERMISSION,
                GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_GOOGLE_SIGN_IN -> {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    if (!account.email.isNullOrEmpty()) {
                        start()
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                    Toast.makeText(context, "Sign in failed " + e.statusCode, Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_FITNESS_PERMISSION -> {
                if (resultCode == Activity.RESULT_OK) {
                    start()
                }
            }
        }
    }

    private fun findFitnessDataSources() {
        Fitness.getSensorsClient(context, GoogleSignIn.getLastSignedInAccount(context)!!)
                .findDataSources(DataSourcesRequest.Builder().setDataTypes(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .setDataSourceTypes(DataSource.TYPE_RAW)
                        .build())
                .addOnSuccessListener { dataSources ->
                    for (dataSource in dataSources) {
                        Log.i(TAG, "Data source found: " + dataSource.toString())
                        if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_CUMULATIVE) && listener == null) {
                            Log.i(TAG, "Data source found!  Registering.")
                            registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        }
                    }
                }
                .addOnFailureListener { e -> Log.e(TAG, "failed", e) }
    }

    private fun registerFitnessDataListener(dataSource: DataSource, dataType: DataType) {
        listener = OnDataPointListener { dataPoint ->
            for (field in dataPoint.dataType.fields) {
                val `val` = dataPoint.getValue(field)
                Log.i(TAG, "Detected DataPoint field: " + field.name)
                Log.i(TAG, "Detected DataPoint value: $`val`")
            }
        }
        Fitness.getSensorsClient(context, GoogleSignIn.getLastSignedInAccount(context)!!)
                .add(SensorRequest.Builder().setDataSource(dataSource)
                        .setDataType(dataType) // Can't be omitted.
                        .setSamplingRate(1, TimeUnit.SECONDS).build(), listener)
                .addOnCompleteListener({ task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "Listener registered!")
                    } else {
                        Log.e(TAG, "Listener not registered.", task.exception)
                    }
                })
    }
}