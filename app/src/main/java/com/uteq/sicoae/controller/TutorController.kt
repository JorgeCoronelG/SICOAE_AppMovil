package com.uteq.sicoae.controller

import android.content.Context
import com.uteq.sicoae.communication.CustomRequest
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.listener.VolleyListener
import com.uteq.sicoae.model.Tutor
import com.uteq.sicoae.util.Constans
import org.json.JSONObject

class TutorController(val context: Context, val listener: DataListener?) : VolleyListener {

    var request: CustomRequest? = null

    init{
        request = CustomRequest(context, this)
    }

    fun storeTutor(id: Int){
        var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        var editor = preferences.edit()
        editor.putInt(Constans.KEY_ACCESS_ID, id)
        editor.commit()
    }

    fun getIdTutor(): Int{
        var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return preferences.getInt(Constans.KEY_ACCESS_ID, 0)
    }

    override fun onSuccess(response: JSONObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}