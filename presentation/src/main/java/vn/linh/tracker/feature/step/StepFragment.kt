package vn.linh.tracker.feature.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.fitness.request.OnDataPointListener
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment
import vn.linh.tracker.infrastructure.StepSensorProvider

class StepFragment : BaseFragment() {
    val TAG = "StepFragment"
    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private var listener: OnDataPointListener? = null
    lateinit var stepSensorProvider: StepSensorProvider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        r()
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    private fun r() {
        stepSensorProvider.start()
    }

    override fun onBackPressed(): Boolean {
        return true
    }


}