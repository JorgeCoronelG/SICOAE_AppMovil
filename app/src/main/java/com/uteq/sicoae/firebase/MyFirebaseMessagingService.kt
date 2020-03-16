package com.uteq.sicoae.firebase

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.uteq.sicoae.R
import com.uteq.sicoae.activities.HomeActivity
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.TokenController
import com.uteq.sicoae.controller.UserController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.util.Constans
import com.uteq.sicoae.util.Notification

class MyFirebaseMessagingService: FirebaseMessagingService(), DataListener {

    private var tokenController: TokenController? = null
    private var userController: UserController? = null
    private var notification: Notification? = null

    val TAG = MyFirebaseMessagingService::class.simpleName

    override fun onNewToken(token: String) {
        Log.d(TAG, token)
        tokenController = TokenController(applicationContext, this)
        userController = UserController(applicationContext, this)
        tokenController?.store(token)
        if(userController!!.isLogged()){
            tokenController?.update(tokenController!!.get())
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notification = Notification(applicationContext)
        if(message.data != null){
            sendNotification(message)
        }
        if(message.notification != null){
            Log.d(TAG, "Body notification: "+ message.notification!!.body)
            sendNotification(message)
        }
    }

    private fun sendNotification(message: RemoteMessage) {
        val data = message.data
        val title = data.get("title")
        val body = data.get("body")
        notification?.createNotification(
            resources.getString(R.string.notification_student),
            resources.getString(R.string.notification_description_student),
            Constans.CHANNEL_ID_REGISTER,
            R.drawable.notification_school,
            title.toString(),
            body.toString())
        val notifyIntent = Intent(applicationContext, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("section", R.id.nav_calendar)
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        notification?.clickToOpenActivity(notifyPendingIntent)
        notification?.showNotification(Constans.NOTIFICATION_ID_REGISTER)
    }

    override fun success(code: Int, obj: Object?) {
        when(code){
            //CommunicationPath.UPDATE_TOKEN.index -> Toast.makeText(this, resources.getString(R.string.update_token_successful), Toast.LENGTH_SHORT).show()
        }
    }

    override fun error(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}