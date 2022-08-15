package com.udacity.asteroidradar.utils.extensions


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.Constants.LAST_DAY
import com.udacity.asteroidradar.utils.Constants.TODAY
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
//    formattedDateList.add("2015-09-07")
//    formattedDateList.add("2015-09-08")
    Log.d("test","formattedDateList $formattedDateList")


    return formattedDateList
}

fun getDates(): HashMap<String, String> {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    val today = dateFormat.format(currentTime)
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val lastTime = calendar.time
    val lastDay = dateFormat.format(lastTime)
    val dates = HashMap<String, String>().apply {
        this[TODAY] = today
        this[LAST_DAY] = lastDay
    }

    return dates
}