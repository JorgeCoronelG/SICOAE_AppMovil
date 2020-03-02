package com.uteq.sicoae.controller

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.communication.CustomRequest
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.listener.VolleyListener
import com.uteq.sicoae.model.Tutor
import com.uteq.sicoae.model.User
import com.uteq.sicoae.util.Constans
import org.json.JSONObject

class UserController(val context: Context, val listener: DataListener?) : VolleyListener {

    var request: CustomRequest? = null

    init{
        request = CustomRequest(context, this)
    }

    fun login(correo: String, clave: String){
        var params = JSONObject()
        params.put("correo", correo)
        params.put("clave", clave)
        val url = CommunicationPath.SERVER.getPath() + CommunicationPath.LOGIN.getPath()
        request?.createRequest(Request.Method.POST, url, params)
    }

    fun resetPassword(correo: String){
        val url = CommunicationPath.SERVER.getPath() + CommunicationPath.RESET_PASSWORD.getPath() + correo
        request?.createRequest(Request.Method.GET, url, null)
    }

    fun isLogged(): Boolean{
        var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        if(preferences.getInt(Constans.KEY_ACCESS_ID, 0) != 0) return true else return false
    }

    fun logout(){
        var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val id = preferences.getInt(Constans.KEY_ACCESS_ID, 0)
        val url = CommunicationPath.SERVER.getPath() + CommunicationPath.LOGOUT.getPath() + id
        request?.createRequest(Request.Method.GET, url, null)
    }

    override fun onSuccess(response: JSONObject) {
        when(response.getInt("code")){
            CommunicationPath.LOGIN.index -> {
                TutorController(context, null).storeTutor(response.getInt("id"))
                listener?.success(CommunicationPath.LOGIN.index, null)
            }
            CommunicationPath.LOGOUT.index -> {
                var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                var editor = preferences.edit()
                editor.putInt(Constans.KEY_ACCESS_ID, 0)
                editor.commit()
                listener?.success(CommunicationPath.LOGOUT.index, null)
            }
            CommunicationPath.RESET_PASSWORD.index -> listener?.success(CommunicationPath.RESET_PASSWORD.index, null)
            CommunicationPath.ERROR.index -> listener?.error(response.getString("error"))
        }
    }

    override fun onError(error: String) {
        listener?.error(error)
    }

}