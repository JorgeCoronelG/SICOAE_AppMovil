package com.uteq.sicoae.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uteq.sicoae.model.Register
import com.uteq.sicoae.R

class RegisterStudentAdapter(var registers: ArrayList<Register>, var resource: Int, var activity: Activity) :
    RecyclerView.Adapter<RegisterStudentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resource, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val register = registers.get(position)
        holder.name?.text = register.student?.nombre
        holder.input?.text = register.entrada
        holder.output?.text = register.salida
    }

    override fun getItemCount(): Int {
        return registers.size
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