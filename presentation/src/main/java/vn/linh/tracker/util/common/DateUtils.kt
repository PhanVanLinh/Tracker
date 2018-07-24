package vn.linh.tracker.util.common

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun getStartOfToday(): Date {
    val today = Calendar.getInstance()
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.set(Calendar.MILLISECOND, 0)
    return today.time
}

fun format(date: Long): String {
    val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return fmt.format(date)
}