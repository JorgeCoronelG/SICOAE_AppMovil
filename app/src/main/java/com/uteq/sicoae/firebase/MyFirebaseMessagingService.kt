package com.uteq.sicoae.firebase

import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.TokenController
import com.uteq.sicoae.controller.UserController
import com.uteq.sicoae.listener.DataListener

class MyFirebaseMessagingService: FirebaseMessagingService(), DataListener {

    private var tokenController: TokenController? = null
    private var userController: UserController? = null

    override fun onNewToken(token: String) {
        tokenController = TokenController(applicationContext, this)
        userController = UserController(applicationContext, this)
        tokenController?.store(token)
        if(userController!!.isLogged()){
            tokenController?.update(tokenController!!.get())
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
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