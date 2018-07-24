package vn.linh.tracker.util.common

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Float.to2Digist(): Float {
    val decimalFormat = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US))
    return java.lang.Float.valueOf(decimalFormat.format(this))
}