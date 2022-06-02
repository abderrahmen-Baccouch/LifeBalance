package com.example.premierepage

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    var myshared: SharedPreferences?=null
    override fun onReceive(context: Context?, intent: Intent?) {
        myshared=context!!.getSharedPreferences("myshared",0)
        var nomTask =myshared?.getString("nomTask","")
        var descTask =myshared?.getString("descTask","")
        val i=Intent(context,App::class.java)
        intent!!.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent=PendingIntent.getActivity(context,0,i,0)

        val builder=NotificationCompat.Builder(context!!,"foxandroid")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(nomTask)
            .setContentText(descTask)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager=NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())



    }
}