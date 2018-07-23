package vn.linh.tracker.feature.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment

class OtherFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_other, container, false)
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}