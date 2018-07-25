package vn.linh.tracker.infrastructure.fit

import android.arch.lifecycle.LiveData
import android.content.Intent
import vn.linh.tracker.model.FitData

interface FitProvider {
    fun start()

    fun stop()

    fun getLiveTodayFitData(): LiveData<FitData>

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}