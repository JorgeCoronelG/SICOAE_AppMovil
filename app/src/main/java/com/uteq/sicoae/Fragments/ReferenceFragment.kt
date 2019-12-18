package com.uteq.sicoae.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

import com.uteq.sicoae.R
import com.uteq.sicoae.Activities.ReferenceActivity

class ReferenceFragment : Fragment(), View.OnClickListener {

    private var spnStudent: Spinner? = null
    private val students = arrayOf("Selecciona:")
    private var btnGenerate: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reference, container, false)

        spnStudent = view.findViewById(R.id.spn_student)
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, students)
        spnStudent?.adapter = arrayAdapter
        btnGenerate = view.findViewById(R.id.btn_generate)
        btnGenerate?.setOnClickListener(this)

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.reference)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_generate -> {
                val intent = Intent(activity, ReferenceActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

}
