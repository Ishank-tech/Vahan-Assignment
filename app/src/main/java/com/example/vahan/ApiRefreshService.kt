package com.example.vahan

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.vahan.MainActivity.Data.colleges
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ApiRefreshService: Service() {
    private var handler: Handler? = null
    private val dataRefreshTask: Runnable = object : Runnable {
        override fun run() {
            val repository = (application as CollegeApplication).collegeRepository
            CoroutineScope(Dispatchers.IO).launch {
                repository.getColleges()
                repository.colleges.value?.let { colleges.addAll(it) }
                Log.d("ISHANK", colleges.size.toString())
            }
            handler?.postDelayed(this, 10000) // 10 seconds
        }
    }
    override fun onCreate() {
        super.onCreate()
        handler = Handler()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler?.post(dataRefreshTask);
        startForeground(1, createNotification());
        return START_STICKY;
    }

    override fun onDestroy() {
        super.onDestroy()
        handler?.removeCallbacks(dataRefreshTask);
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification? {
        val channel = NotificationChannel(
            "channel_id",
            "Foreground Service",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.ic_refresh)
            .setContentTitle("Data Refresh Service")
            .setContentText("Refreshing data from the API")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

}