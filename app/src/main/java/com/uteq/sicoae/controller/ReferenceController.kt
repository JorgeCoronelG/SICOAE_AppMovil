package com.uteq.sicoae.controller

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.communication.CustomRequest
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.listener.VolleyListener
import com.uteq.sicoae.model.Reference
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.model.Tutor
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

    fun get(id: Int){
        val url = CommunicationPath.GET_REFERENCES.getPath()+id
        request?.createRequest(Request.Method.GET, url, null)
    }

    fun update(id: String, persona: String){
        var params = JSONObject()
        params.put("id", id)
        params.put("persona", persona)
        val url = CommunicationPath.UPDATE_REFERENCE.getPath()
        request?.createRequest(Request.Method.POST, url, params)
    }

    override fun onSuccess(response: JSONObject) {
        when(response.getInt("code")){
            CommunicationPath.CREATE_REFERENCE.index -> {
                var referencia = Reference()
                referencia.id = response.getString("referencia")
                listener?.success(CommunicationPath.CREATE_REFERENCE.index, referencia as Object)
            }
            CommunicationPath.GET_REFERENCES.index -> {
                var data = response.getJSONArray("referencias")
                if(!data.isNull(0)){
                    var tutor = Tutor()
                    var estudiantes = ArrayList<Student>()
                    for(i in 0..(data.length()-1)){
                        var dataArray = data.getJSONObject(i)
                        var estudiante = Student()
                        estudiante.matricula = dataArray.getString("matricula")
                        estudiante.nombre = dataArray.getString("nombre")

                        var referencia = Reference()
                        referencia.id = dataArray.getString("referencia")
                        referencia.fecha = dataArray.getString("fecha")
                        referencia.persona = dataArray.getString("persona")
                        estudiante.reference = referencia

                        estudiantes.add(estudiante)
                    }
                    tutor.estudiantes = estudiantes
                    listener?.success(CommunicationPath.GET_REFERENCES.index, tutor as Object)
                }else{
                    listener?.success(CommunicationPath.GET_REFERENCES.index, null)
                }
            }
            CommunicationPath.UPDATE_REFERENCE.index -> listener?.success(CommunicationPath.UPDATE_REFERENCE.index, null)
            CommunicationPath.ERROR.index -> listener?.error(response.getString("error"))
        }
    }

    override fun onError(error: String) {
        listener?.error(error)
    }
}