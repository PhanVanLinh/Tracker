package vn.linh.tracker.feature.step

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_step.*
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment
import vn.linh.tracker.infrastructure.fit.FitProvider
import vn.linh.tracker.model.FitData
import javax.inject.Inject

class StepFragment : BaseFragment() {
    val TAG = "StepFragment"

    @Inject
    lateinit var fitProvider: FitProvider

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        start()
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    private fun start() {
        fitProvider.start()
        fitProvider.getLiveTodayFitData().observe(this, Observer {
            it?.let {
                onFitDataChange(it)
            }
        })
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    private fun onFitDataChange(fitData: FitData) {
        text_step.text = fitData.getDisplayStep()
        text_calorie.text = fitData.getDisplayCalory()
        text_distance.text = fitData.getDisplayDistance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fitProvider.onActivityResult(requestCode, resultCode, data)
    }
}