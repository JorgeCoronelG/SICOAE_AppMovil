package com.uteq.sicoae.communication

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class Communication(val mContext: Context) {

    private var mInstance: Communication? = null
    private var request: RequestQueue? = null

    init {
        request = getRequestQueue()
    }

    @Synchronized
    fun getmInstance(context: Context?): Communication? {
        if (mInstance == null) {
            mInstance = Communication(context!!)
        }
        return mInstance
    }

    private fun getRequestQueue(): RequestQueue? {
        if(request == null){
            request = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return request;
    }

    fun <T> addToRequestQueue(request: Request<T>?) {
        getRequestQueue()!!.add(request)
    }

}