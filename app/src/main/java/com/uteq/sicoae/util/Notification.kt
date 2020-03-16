package com.uteq.sicoae.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.uteq.sicoae.R
import java.io.File

class Notification(var context: Context) {

    var mBuilder: NotificationCompat.Builder? = null
    val notificacionManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createNotification(name: String, description: String, channelId: String, smallIcon: Int, title: String, content: String){
        mBuilder = NotificationCompat.Builder(context, "")
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            var channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.enableLights(true)
            channel.lightColor = Color.WHITE
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(0,250,100,250)
            channel.setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build())
            notificacionManager.createNotificationChannel(channel)
            mBuilder = NotificationCompat.Builder(context, channelId)
        }
        mBuilder!!
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(0,250,100,250))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setColor(context.resources.getColor(R.color.colorPrimary))
            .setOngoing(false)
    }

    fun addLargeIconAndStyle(bitmap: Bitmap){
        mBuilder!!
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigPictureStyle()
            .bigPicture(bitmap)
            .bigLargeIcon(null))
    }

    fun clickToImage(imageFile: File){
        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        //val uri = Uri.fromFile(imageFile)
        val uri = FileProvider.getUriForFile(context, context.packageName+".provider", imageFile)
        intent.setDataAndType(uri, "image/*")
        //intent.setType("image/*")
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder!!
            .setContentIntent(pendingIntent)
    }

    fun clickToOpenActivity(pendingIntent: PendingIntent){
        mBuilder!!.setContentIntent(pendingIntent)
    }

    fun showNotification(notificationId: Int){
        notificacionManager.notify(notificationId, mBuilder!!.build())
    }

}