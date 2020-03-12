package com.uteq.sicoae.controller

import android.content.Context
import com.android.volley.Request
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.communication.CustomRequest
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.listener.VolleyListener
import com.uteq.sicoae.model.Reference
import org.json.JSONObject

class ReferenceController (val context: Context, val listener: DataListener?): VolleyListener{

    var request: CustomRequest? = null

    init{
        request = CustomRequest(context, this)
    }

    fun create(matricula: String, persona: String){
        var params = JSONObject()
        params.put("matricula", matricula)
        params.put("persona", persona)
        val url = CommunicationPath.CREATE_REFERENCE.getPath()
        request?.createRequest(Request.Method.POST, url, params)
    }

    override fun onSuccess(response: JSONObject) {
        when(response.getInt("code")){
            CommunicationPath.CREATE_REFERENCE.index -> {
                var referencia = Reference()
                referencia.id = response.getString("referencia")
                listener?.success(CommunicationPath.CREATE_REFERENCE.index, referencia as Object)
            }
            CommunicationPath.ERROR.index -> listener?.error(response.getString("error"))
        }
    }

    override fun onError(error: String) {
        listener?.error(error)
    }
}