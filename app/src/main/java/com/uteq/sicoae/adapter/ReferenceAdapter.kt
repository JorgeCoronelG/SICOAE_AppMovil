package com.uteq.sicoae.adapter

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.uteq.sicoae.R
import com.uteq.sicoae.model.Student

class ReferenceAdapter(var students: ArrayList<Student>, var resource: Int, var activity: Activity, var onReferenceListener: OnReferenceListener) :
    RecyclerView.Adapter<ReferenceAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(resource, parent, false)
        return ViewHolder(view, onReferenceListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students.get(position)
        holder.reference?.text = student.reference?.id
        holder.student?.text = student.nombre
        holder.person?.text = student.reference?.persona
    }

    override fun getItemCount(): Int {
        return students.size
    }

    inner class ViewHolder(itemView: View, onReferenceListener: OnReferenceListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var view = itemView
        var reference: TextView? = null
        var student: TextView? = null
        var person: TextView? = null
        var edit: ImageView? = null
        var onReferenceListener: OnReferenceListener? = null

        init{
            reference = view.findViewById(R.id.txt_reference)
            student = view.findViewById(R.id.txt_student)
            person = view.findViewById(R.id.txt_person)
            edit = view.findViewById(R.id.img_edit)
            this.onReferenceListener = onReferenceListener

            edit?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.onReferenceListener?.onReferenceClick(adapterPosition)
        }

    }

    interface OnReferenceListener{
        fun onReferenceClick(position: Int)
    }

}