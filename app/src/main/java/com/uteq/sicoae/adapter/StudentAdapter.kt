package com.uteq.sicoae.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.R

class StudentAdapter(var students: ArrayList<Student>, var resource: Int, var activity: Activity) :
    androidx.recyclerview.widget.RecyclerView.Adapter<StudentAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resource, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students.get(position)
        holder.name?.text = student.nombre
        holder.scholarship?.text = "${student.grado}°${student.grupo}"
    }

    override fun getItemCount(): Int {
        return students.size
    }

    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){

        var view = itemView
        var name: TextView? = null
        var scholarship: TextView? = null

        init {
            name = view.findViewById(R.id.txt_name)
            scholarship = view.findViewById(R.id.txt_scholarship)
        }

    }

}