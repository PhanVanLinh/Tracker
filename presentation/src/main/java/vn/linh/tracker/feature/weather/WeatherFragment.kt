package vn.linh.tracker.feature.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment

class WeatherFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onBackPressed() : Boolean {
        return true
    }
}