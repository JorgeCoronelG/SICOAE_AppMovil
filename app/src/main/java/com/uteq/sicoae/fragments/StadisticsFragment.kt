package com.uteq.sicoae.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.TutorController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.model.Tutor
import dmax.dialog.SpotsDialog

class StadisticsFragment : Fragment(), View.OnClickListener, DataListener {

    var spnPeriod: Spinner? = null
    var spnStudent: Spinner? = null
    val periods = arrayOf("Última semana","Último mes")
    private var students = ArrayList<Student>()
    var btnGenerate: Button? = null
    private var tutorController: TutorController? = null
    private var dialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stadistics, container, false)

        spnPeriod = view.findViewById(R.id.spn_period)
        spnStudent = view.findViewById(R.id.spn_student)
        btnGenerate = view.findViewById(R.id.btn_generate)

        var arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, periods)
        spnPeriod?.adapter = arrayAdapter

        tutorController = TutorController(context!!, this)
        createDialog()
        tutorController?.get()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.stadistics)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_generate -> {

            }
        }
    }

    override fun success(code: Int, obj: Object?) {
        dialog?.dismiss()
        when(code){
            CommunicationPath.GET_TUTOR.index -> {
                val tutor = obj as Tutor
                students = tutor.estudiantes!!
                var arrayStudents = arrayOfNulls<String>(students.size)
                for(i in 0..(students.size-1)){
                    arrayStudents.set(i, students.get(i).nombre.toString())
                }
                val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayStudents)
                spnStudent?.adapter = arrayAdapter
            }
        }
    }

    override fun error(error: String) {
        dialog?.dismiss()
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    fun createDialog(){
        dialog = SpotsDialog.Builder()
            .setContext(context!!)
            .setMessage(resources.getString(R.string.wait_a_moment))
            .setCancelable(false)
            .build()
        dialog!!.show()
    }

}