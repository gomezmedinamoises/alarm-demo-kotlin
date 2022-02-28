package com.mgomezm.alarmdemokotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mgomezm.alarmdemokotlin.util.Constants
import io.karn.notify.Notify
import java.lang.String.format
import android.text.format.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.EXTRA_EXACT_ALARM_TIME, 0L)
        when (intent.action) {
            Constants.ACTION_SET_EXACT_ALARM -> {
                buildNotification(context, "Set exact time", convertDate(timeInMillis))
            }

            Constants.ACTION_SET_REPETITIVE_EXACT -> {
                val cal = Calendar.getInstance().apply {
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
                }
                AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)

                buildNotification(context, "Set repetitive time", convertDate(timeInMillis))
            }
        }
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        Notify
            .with(context)
            .content {
                this.title= title
                this.text = message
            }
            .show()
    }

    private fun convertDate(timeInMillis: Long): String =
        DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()

}