package vn.linh.tracker.feature.step

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.fitness.request.OnDataPointListener
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment
import vn.linh.tracker.infrastructure.StepSensorProvider
import javax.inject.Inject

class StepFragment : BaseFragment() {
    val TAG = "StepFragment"
    private val REQUEST_OAUTH_REQUEST_CODE = 1
    private var listener: OnDataPointListener? = null

    @Inject
    lateinit var stepSensorProvider: StepSensorProvider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        start()
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    private fun start() {
        stepSensorProvider.start()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        stepSensorProvider.onActivityResult(requestCode, resultCode, data)
    }
}