package vn.linh.tracker.infrastructure.fit.client

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.SensorsClient
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import vn.linh.data.utils.action.Action1
import vn.linh.tracker.infrastructure.fit.GoogleFitProvider
import java.util.concurrent.TimeUnit

class GoogleFitSensorClient constructor(val context: Context, private val stepChangeCallback: ((Int) -> Unit)) {
    val TAG = "GoogleFitSensorClient"
    private val listener = OnDataPointListener { dataPoint ->
        for (field in dataPoint.dataType.fields) {
            val value = dataPoint.getValue(field)
            Log.i(TAG, "Detected DataPoint field: " + field.name)
            Log.i(TAG, "Detected DataPoint value: $value")
            if (field.name == GoogleFitProvider.Key.STEP.value) {
                stepChangeCallback.invoke(value.asInt())
            }
        }
    }

    fun observeStepChange() {
        findFitnessDataSources(DataType.TYPE_STEP_COUNT_DELTA, object : Action1<DataSource> {
            override fun call(t: DataSource) {
                registerFitnessDataListener(t, DataType.TYPE_STEP_COUNT_DELTA)
            }
        })
    }

    fun unObserveStepChange() {
        unRegisterFitnessDataListener()
    }

    private fun findFitnessDataSources(dataType: DataType, action: Action1<DataSource>) {
        Log.i(TAG, "findFitnessDataSources ")
        val request = DataSourcesRequest.Builder()
                .setDataTypes(dataType)
                .build()
        getSensorClient().findDataSources(request)
                .addOnSuccessListener { dataSources ->
                    Log.i(TAG, "Data source found: " + dataSources.size)
                    for (dataSource in dataSources) {
                        Log.i(TAG, "Data source found: " + dataSource.toString())
                        if (dataSource.dataType == dataType) {
                            action.call(dataSource)
                        }
                    }
                }
                .addOnFailureListener { e -> e.printStackTrace() }
    }

    private fun registerFitnessDataListener(dataSource: DataSource, dataType: DataType) {
        Log.i(TAG, "Registering: " + dataSource.dataType)
        getSensorClient().add(SensorRequest.Builder().setDataSource(dataSource)
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

    private fun unRegisterFitnessDataListener() {
        getSensorClient().remove(listener).addOnCompleteListener(
                { task ->
                    if (task.isSuccessful && task.result) {
                        Log.i(TAG, "Listener was removed!")
                    } else {
                        Log.i(TAG, "Listener was not removed.")
                    }
                })
    }

    private fun getSensorClient(): SensorsClient {
        return Fitness.getSensorsClient(context, GoogleSignIn.getLastSignedInAccount(context)!!)
    }
}