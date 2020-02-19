package com.uteq.sicoae.listener

import org.json.JSONObject

interface VolleyListener {

    fun onSuccess(response: JSONObject)
    fun onError(error: String)

}