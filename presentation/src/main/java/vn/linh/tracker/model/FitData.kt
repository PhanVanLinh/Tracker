package vn.linh.tracker.model

import vn.linh.tracker.util.common.to2Digist

class FitData(
        var step: Int = 0,
        var calory: Float = 0f,
        var distance: Float = 0f
) {
    fun getDisplayStep(): String {
        return step.toString()
    }

    fun getDisplayCalory(): String {
        return calory.to2Digist().toString()
    }

    fun getDisplayDistance(): String {
        return distance.to2Digist().toString()
    }
}