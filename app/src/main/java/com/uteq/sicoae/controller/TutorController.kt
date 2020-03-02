package com.uteq.sicoae.controller

import android.content.Context
import com.android.volley.Request
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.communication.CustomRequest
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.listener.VolleyListener
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.model.Tutor
import com.uteq.sicoae.model.User
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

    fun get(){
        val url = CommunicationPath.GET_TUTOR.getPath() + getId()
        request?.createRequest(Request.Method.GET, url, null)
    }

    fun getId(): Int{
        var preferences = context.getSharedPreferences(Constans.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return preferences.getInt(Constans.KEY_ACCESS_ID, 0)
    }

    override fun onSuccess(response: JSONObject) {
        when(response.getInt("code")){
            CommunicationPath.GET_TUTOR.index -> {
                var tutor = Tutor()
                tutor.id = response.getInt("id")
                tutor.nombre = response.getString("nombre")
                tutor.telefono = response.getString("telefono")
                tutor.usuario = User(response.getString("correo"), null)
                var data = response.getJSONArray("estudiantes")
                var estudiantes = ArrayList<Student>()
                for(i in 0..(data.length() - 1)){
                    var dataArray = data.getJSONObject(i)
                    var estudiante = Student()
                    estudiante.matricula = dataArray.getString("matricula")
                    estudiante.tarjeta = dataArray.getString("tarjeta")
                    estudiante.nombre = dataArray.getString("nombre")
                    estudiante.grado = dataArray.getInt("grado")
                    estudiante.grupo = dataArray.getString("grupo")
                    estudiantes.add(estudiante)
                }
                tutor.estudiantes = estudiantes
                listener?.success(CommunicationPath.GET_TUTOR.index, tutor as Object)
            }
            CommunicationPath.ERROR.index -> listener?.error(response.getString("error"))
        }
    }

    override fun onError(error: String) {
        listener?.error(error)
    }
}