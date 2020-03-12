package com.uteq.sicoae.controller

import android.content.Context
import com.android.volley.Request
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.communication.CustomRequest
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.listener.VolleyListener
import com.uteq.sicoae.model.Register
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.model.Tutor
import org.json.JSONObject

class RegisterController (val context: Context, val listener: DataListener?): VolleyListener {

    var request: CustomRequest? = null

    init{
        request = CustomRequest(context, this)
    }

    fun getDayRegister(id: Int, date: String){
        val url = CommunicationPath.GET_DAY_REGISTER.getPath()+"${id}/${date}"
        request?.createRequest(Request.Method.GET, url, null)
    }

    override fun onSuccess(response: JSONObject) {
        when(response.getInt("code")){
            CommunicationPath.GET_DAY_REGISTER.index -> {
                var tutor = Tutor()
                var data = response.getJSONArray("registros")
                var estudiantes = ArrayList<Student>()
                for(i in 0..(data.length() - 1)){
                    var dataArray = data.getJSONObject(i)

                    var estudiante = Student()
                    estudiante.matricula = dataArray.getString("matricula")
                    estudiante.nombre = dataArray.getString("nombre")

                    var registro = Register()
                    registro.id = dataArray.getInt("registro")
                    registro.fecha = dataArray.getString("fecha")
                    registro.entrada = dataArray.getString("hora_entrada")
                    registro.salida = dataArray.getString("hora_salida")

                    estudiante.register = registro
                    estudiantes.add(estudiante)
                }
                tutor.estudiantes = estudiantes
                listener?.success(CommunicationPath.GET_DAY_REGISTER.index, tutor as Object)
            }
            CommunicationPath.ERROR.index -> listener?.error(response.getString("error"))
        }
    }

    override fun onError(error: String) {
        listener?.error(error)
    }
}