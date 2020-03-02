package com.uteq.sicoae.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast

import com.uteq.sicoae.R
import com.uteq.sicoae.activities.ChangePasswordActivity
import com.uteq.sicoae.activities.HomeActivity
import com.uteq.sicoae.adapter.StudentAdapter
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.TutorController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.model.Tutor
import dmax.dialog.SpotsDialog
import java.util.ArrayList

class ProfileFragment : Fragment(), DataListener {

    private var recyclerStudents: RecyclerView? = null
    private var tutorController: TutorController? = null
    private var dialog: AlertDialog? = null
    private var txtName: TextView? = null
    private var txtPhone: TextView? = null
    private var txtEmail: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        txtName = view.findViewById(R.id.txt_name)
        txtPhone = view.findViewById(R.id.txt_phone)
        txtEmail = view.findViewById(R.id.txt_email)
        recyclerStudents = view.findViewById(R.id.recycler_students)
        tutorController = TutorController(context!!, this)
        createDialog()
        tutorController?.get()
        setHasOptionsMenu(true)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.my_profile)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_change_password, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_change_password -> {
                var intent = Intent(context, ChangePasswordActivity::class.java)
                intent.putExtra("correo", txtEmail?.text)
                startActivity(intent)
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun success(code: Int, obj: Object?) {
        dialog?.dismiss()
        when(code){
            CommunicationPath.GET_TUTOR.index -> {
                val tutor = obj as Tutor
                txtName?.text = tutor.nombre
                txtPhone?.text = tutor.telefono
                txtEmail?.text = tutor.usuario?.correo
                var manager = LinearLayoutManager(context)
                manager.orientation = LinearLayoutManager.VERTICAL
                recyclerStudents?.layoutManager = manager
                val adapter = StudentAdapter(tutor.estudiantes!!, R.layout.cardview_student, activity!!)
                recyclerStudents?.adapter = adapter
            }
        }
    }

    override fun error(error: String) {
        dialog?.dismiss()
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    fun createDialog(){
        dialog = SpotsDialog.Builder()
            .setContext(context)
            .setMessage(resources.getString(R.string.wait_a_moment))
            .setCancelable(false)
            .build()
        dialog!!.show()
    }

}
