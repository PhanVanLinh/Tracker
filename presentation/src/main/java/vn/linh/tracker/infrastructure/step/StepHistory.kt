package vn.linh.tracker.infrastructure.step

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.Bucket
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.data.DataType
import vn.linh.tracker.model.FitData
import vn.linh.tracker.util.common.format
import vn.linh.tracker.util.common.getCurrentDateTime
import vn.linh.tracker.util.common.getStartOfToday
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StepHistory constructor(val context: Context, private val movieClickCallback: ((FitData) -> Unit)) {
    val TAG = "StepHistory"

    fun readTodayHistory() {
        val startTime = getStartOfToday().time
        val endTime = getCurrentDateTime().time
        Log.i(TAG, "Start: "
                + format(startTime)
                + "  End: "
                + format(endTime))
        val ESTIMATED_STEP_DELTAS = DataSource.Builder()
                .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .setType(DataSource.TYPE_DERIVED)
                .setStreamName("estimated_steps")
                .setAppPackageName("com.google.android.gms")
                .build()
        val readRequest = DataReadRequest.Builder()
                .aggregate(ESTIMATED_STEP_DELTAS, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()

        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context)!!)
                .readData(readRequest)
                .addOnSuccessListener({ response ->
                    for (bucket in response.buckets) {
                        dumpDataSet(bucket)
                    }
                })
                .addOnFailureListener({ e -> Log.e(TAG, "onFailure()", e) })
    }

    private fun dumpDataSet(bucket: Bucket) {
        val fitData = FitData()
        for (dps in bucket.dataSets) {
            for (dp in dps.dataPoints) {
                Log.i(TAG, "Type: " + dp.dataType.name)
                Log.i(TAG, "Start: "
                        + format(dp.getStartTime(TimeUnit.MILLISECONDS))
                        + "  End: "
                        + format(dp.getEndTime(TimeUnit.MILLISECONDS)))
                for (field in dp.dataType.fields) {
                    Log.i(TAG, "Field:" + field.name + " Value:" + dp.getValue(field))
                    if (dp.dataType == DataType.TYPE_STEP_COUNT_DELTA) {
                        fitData.step += dp.getValue(field).asInt()
                    }
                    if (dp.dataType == DataType.TYPE_CALORIES_EXPENDED) {
                        fitData.calory += dp.getValue(field).asFloat()
                    }
                    if (dp.dataType == DataType.TYPE_DISTANCE_DELTA) {
                        fitData.distance += dp.getValue(field).asFloat()
                    }
                }
            }
        }
        movieClickCallback.invoke(fitData)
    }
}