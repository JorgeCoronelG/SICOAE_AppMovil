package com.uteq.sicoae.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.uteq.sicoae.R

class StadisticsFragment : Fragment() {

    var spnPeriod: Spinner? = null
    var spnStudents: Spinner? = null
    val periods = arrayOf("Selecciona:","Última semana","Último mes")
    val students = arrayOf("Selecciona:")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stadistics, container, false)

        spnPeriod = view.findViewById(R.id.spn_period)
        spnStudents = view.findViewById(R.id.spn_student)
        var arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, periods)
        spnPeriod?.adapter = arrayAdapter
        arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, students)
        spnStudents?.adapter = arrayAdapter

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.stadistics)
    }

}
