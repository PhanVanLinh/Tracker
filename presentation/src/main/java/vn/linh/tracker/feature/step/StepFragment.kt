package vn.linh.tracker.feature.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment

class StepFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_step, container, false)
    }

    override fun onBackPressed() : Boolean {
        return true
    }
}