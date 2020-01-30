package com.uteq.sicoae.communication

import android.content.Context
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject


class CustomRequest(val context: Context, val listener: VolleyListener) : Response.Listener<JSONObject>, Response.ErrorListener{

    private var request: JsonObjectRequest? = null

    fun createRequest(method: Int, url: String, params: JSONObject){
        request = JsonObjectRequest(method, url, params, this, this)
        Communication(context).getmInstance(context)!!.addToRequestQueue(request)
    }

    override fun onResponse(response: JSONObject?) {
        listener.onSuccess(response!!)
    }

    override fun onErrorResponse(error: VolleyError?) {
        listener.onError(error.toString())
    }

    interface VolleyListener{
        fun onSuccess(response: JSONObject)
        fun onError(error: String)
    }

}