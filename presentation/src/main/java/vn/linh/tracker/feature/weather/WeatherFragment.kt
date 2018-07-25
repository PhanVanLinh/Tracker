package vn.linh.tracker.feature.weather

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.linh.domain.usecase.GetWeatherUseCase
import vn.linh.tracker.R
import vn.linh.tracker.feature.BaseFragment
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider

class WeatherFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: WeatherViewModel
        get() = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.getWeather()
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}