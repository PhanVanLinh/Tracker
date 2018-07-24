package vn.linh.tracker.infrastructure.step

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.Bucket
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataSourcesRequest
import com.google.android.gms.fitness.request.OnDataPointListener
import com.google.android.gms.fitness.request.SensorRequest
import vn.linh.tracker.model.FitData
import vn.linh.tracker.util.common.format
import vn.linh.tracker.util.common.getCurrentDateTime
import vn.linh.tracker.util.common.getStartOfToday
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StepSensor constructor(val context: Context, private val movieClickCallback: ((FitData) -> Unit)) {
    val TAG = "StepSensor"
    val listener = OnDataPointListener { dataPoint ->
        for (field in dataPoint.dataType.fields) {
            val `val` = dataPoint.getValue(field)
            Log.i(TAG, "Detected DataPoint field: " + field.name)
            Log.i(TAG, "Detected DataPoint value: $`val`")
        }
    }

    fun start() {
        findFitnessDataSources()
    }

    private fun findFitnessDataSources() {
        Fitness.getSensorsClient(context, GoogleSignIn.getLastSignedInAccount(context)!!)
                .findDataSources(DataSourcesRequest.Builder().setDataTypes(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        .setDataSourceTypes(DataSource.TYPE_RAW)
                        .build())
                .addOnSuccessListener { dataSources ->
                    for (dataSource in dataSources) {
                        Log.i(TAG, "Data source found: " + dataSource.toString())
                        if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_CUMULATIVE)) {
                            Log.i(TAG, "Data source found!  Registering.")
                            registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE)
                        }
                    }
                }
                .addOnFailureListener { e -> Log.e(TAG, "failed", e) }
    }

    private fun registerFitnessDataListener(dataSource: DataSource, dataType: DataType) {
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