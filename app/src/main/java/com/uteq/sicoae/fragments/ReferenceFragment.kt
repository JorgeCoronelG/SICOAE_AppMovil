package com.uteq.sicoae.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.*
import com.uteq.sicoae.activities.HistoricalReferencesActivity

import com.uteq.sicoae.R
import com.uteq.sicoae.activities.ReferenceActivity
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.ReferenceController
import com.uteq.sicoae.controller.TutorController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.model.Reference
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.model.Tutor
import dmax.dialog.SpotsDialog

class ReferenceFragment : Fragment(), View.OnClickListener, DataListener {

    private var spnStudent: Spinner? = null
    private var students = ArrayList<Student>()
    private var etPerson: EditText? = null
    private var btnGenerate: Button? = null
    private var tutorController: TutorController? = null
    private var referenceController: ReferenceController? = null
    private var dialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reference, container, false)

        spnStudent = view.findViewById(R.id.spn_student)
        btnGenerate = view.findViewById(R.id.btn_generate)
        etPerson = view.findViewById(R.id.et_person)

        tutorController = TutorController(context!!, this)
        referenceController = ReferenceController(context!!, this)

        createDialog()
        tutorController?.get()
        btnGenerate?.setOnClickListener(this)

        setHasOptionsMenu(true)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.reference)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_generate -> {
                if(checkField()){
                    createDialog()
                    val matricula = students.get(spnStudent!!.selectedItemPosition).matricula!!
                    val persona = etPerson?.text.toString()
                    referenceController?.create(matricula, persona)
                }
            }
        }
    }

    fun checkField(): Boolean{
        etPerson?.setError(null)
        if(etPerson?.text.toString().isEmpty()){
            etPerson?.setError(resources.getString(R.string.required_field))
            return false
        }else{
            return true
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
            CommunicationPath.CREATE_REFERENCE.index -> {
                val reference = obj as Reference
                var intent = Intent(activity, ReferenceActivity::class.java)
                intent.putExtra("referencia", reference.id)
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    override fun error(error: String) {
        dialog?.dismiss()
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_historical_references, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_historical_references -> {
                val intent = Intent(context, HistoricalReferencesActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
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