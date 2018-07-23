package vn.linh.tracker.infrastructure

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import java.util.concurrent.TimeUnit

class StepSensorProvider(var activity: Activity, var context: Context) {
    val TAG = "StepFragment"
    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private var listener: OnDataPointListener? = null

     fun start(){
        if (hasOAuthPermission()) {
            findFitnessDataSources()
        } else {
            requestOAuthPermission()
        }
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
        GoogleSignIn.requestPermissions(activity, REQUEST_OAUTH_REQUEST_CODE,
                GoogleSignIn.getLastSignedInAccount(context), fitnessOptions)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                findFitnessDataSources()
            }
        }
    }

    private fun findFitnessDataSources() {
        Fitness.getSensorsClient(activity, GoogleSignIn.getLastSignedInAccount(context)!!)
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
            Fitness.getSensorsClient(activity, GoogleSignIn.getLastSignedInAccount(context)!!)
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
}