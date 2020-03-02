package com.uteq.sicoae.controller

import android.content.Context
import com.android.volley.Request
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.communication.CustomRequest
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.listener.VolleyListener
import com.uteq.sicoae.model.Token
import com.uteq.sicoae.model.Tutor
import com.uteq.sicoae.util.Constans
import org.json.JSONObject

class TokenController(val context: Context, val listener: DataListener?) : VolleyListener{

    var request: CustomRequest? = null

    init{
        request = CustomRequest(context, this)
    }

    fun store(token: String){
        var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        var editor = preferences.edit()
        editor.putString(Constans.KEY_ACCESS_TOKEN, token)
        editor.commit()
    }

    fun get(): Token{
        var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return Token(
            preferences.getString(Constans.KEY_ACCESS_TOKEN, null),
            Tutor(preferences.getInt(Constans.KEY_ACCESS_ID, 0), null, null, null, null)
        )
    }

    fun update(token: Token){
        var params = JSONObject()
        params.put("id", token.tutor!!.id)
        params.put("token", token.token)
        val url = CommunicationPath.SERVER.getPath() + CommunicationPath.UPDATE_TOKEN.getPath()
        request?.createRequest(Request.Method.POST, url, params)
    }

    override fun onSuccess(response: JSONObject) {
        when(response.getInt("code")){
            CommunicationPath.UPDATE_TOKEN.index -> listener?.success(CommunicationPath.UPDATE_TOKEN.index, null)
        }
    }

    override fun onError(error: String) {
        listener?.error(error)
    }

}