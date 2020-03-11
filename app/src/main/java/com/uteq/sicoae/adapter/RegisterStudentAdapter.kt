package com.uteq.sicoae.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uteq.sicoae.R
import com.uteq.sicoae.model.Student

class RegisterStudentAdapter(var students: ArrayList<Student>, var resource: Int, var activity: Activity) :
    RecyclerView.Adapter<RegisterStudentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resource, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students.get(position)
        holder.name?.text = student.nombre

        if(!student.register!!.entrada!!.isEmpty())
            holder.input?.text = "Entrada: ${student.register?.entrada}"
        else
            holder.input?.text = "Entrada: "+activity.resources.getString(R.string.without_registration)

        if(!student.register!!.salida!!.isEmpty())
            holder.output?.text = "Salida: ${student.register?.salida}"
        else
            holder.output?.text = "Salida: "+activity.resources.getString(R.string.without_registration)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var view = itemView
        var name: TextView? = null
        var input: TextView? = null
        var output: TextView? = null

        init {
            name = view.findViewById(R.id.txt_name)
            input = view.findViewById(R.id.txt_input)
            output = view.findViewById(R.id.txt_output)
        }

    }

}