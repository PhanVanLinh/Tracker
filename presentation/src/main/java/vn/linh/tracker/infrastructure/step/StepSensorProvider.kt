package vn.linh.tracker.infrastructure.step

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
import vn.linh.tracker.model.FitData
import vn.linh.tracker.util.constant.REQUEST_CODE_FITNESS_PERMISSION
import vn.linh.tracker.util.constant.REQUEST_CODE_GOOGLE_SIGN_IN
import java.util.concurrent.TimeUnit

class StepSensorProvider(var fragment: Fragment,
                         var context: Context) {
    val TAG = "StepFragment"
    private var listener: OnDataPointListener? = null

    interface OnFitDataChangeListener {
        fun onFitDataChange(fitData: FitData)
    }

    var fitDataChangeListener: OnFitDataChangeListener? = null

    var stepHistory: StepHistory = StepHistory(context, {
        fitDataChangeListener?.onFitDataChange(it)
        stepSensor.start()
    })

    var stepSensor: StepSensor = StepSensor(context, {
        fitDataChangeListener?.onFitDataChange(it)
    })

    fun start() {
        if (!isSignedIn()) {
            signIn()
            return
        }
        if (!hasOAuthPermission()) {
            requestOAuthPermission()
            return
        }
        stepHistory.readTodayHistory()
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
        return FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED)
                .addDataType(DataType.TYPE_DISTANCE_CUMULATIVE)
                .build()
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
}