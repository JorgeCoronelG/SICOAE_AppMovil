package com.uteq.sicoae.firebase

import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.TokenController
import com.uteq.sicoae.controller.UserController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.model.Token
import dmax.dialog.SpotsDialog

class MyFirebaseMessagingService: FirebaseMessagingService(), DataListener {

    private var dialog: AlertDialog? = null
    private var tokenController: TokenController? = null
    private var userController: UserController? = null

    override fun onNewToken(token: String) {
        tokenController = TokenController(applicationContext, this)
        userController = UserController(applicationContext, this)
        tokenController?.store(token)
        if(userController!!.isLogged()){
            createDialog()
            tokenController?.update(tokenController!!.get())
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    fun createDialog(){
        dialog = SpotsDialog.Builder()
            .setContext(applicationContext)
            .setMessage(resources.getString(R.string.wait_a_moment))
            .setCancelable(false)
            .build()
        dialog!!.show()
    }

    override fun success(code: Int, obj: Object?) {
        dialog?.dismiss()
        when(code){
            CommunicationPath.UPDATE_TOKEN.index -> Toast.makeText(this, resources.getString(R.string.update_token_successful), Toast.LENGTH_SHORT).show()
        }
    }

    override fun error(error: String) {
        dialog?.dismiss()
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}