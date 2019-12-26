package com.uteq.sicoae.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.uteq.sicoae.adapter.RegisterStudentAdapter
import com.uteq.sicoae.model.Register
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.R

class RegisterStudentDialogFragment : DialogFragment(), View.OnClickListener {

    private var btnClose: Button? = null
    private var txtDay: TextView? = null
    private var txtDate: TextView? = null
    private var dayOfWeek: String? = null
    private var date: String? = null
    private var recyclerStudents: RecyclerView? = null

    val TAG = RegisterStudentDialogFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(R.layout.dialog_fragment_student, container, false)

        btnClose = view.findViewById(R.id.btn_close)
        txtDay = view.findViewById(R.id.txt_day)
        txtDate = view.findViewById(R.id.txt_date)
        recyclerStudents = view.findViewById(R.id.recycler_students)

        txtDay?.text = dayOfWeek
        txtDate?.text = date

        btnClose?.setOnClickListener(this)

        var manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerStudents?.layoutManager = manager
        val adapter = RegisterStudentAdapter(buildRegisters(), R.layout.cardview_register_student, activity!!)
        recyclerStudents?.adapter = adapter

        return view;
    }

    private fun buildRegisters(): ArrayList<Register>{
        var registers = ArrayList<Register>()
        registers.add(Register(null, null,"Entrada: 08:00 am","Salida: 12:30 pm",
            Student(null,null,"Jorge Coronel González",null,null)))
        registers.add(Register(null, null,"Entrada: 07:55 am","Salida: 12:25 pm",
            Student(null,null,"Lucía Guadalupe Salinas Reyes",null,null)))
        registers.add(Register(null, null,"Entrada: 07:50 am","Salida: 12:33 pm",
            Student(null,null,"Estefanía Coronel González",null,null)))
        return registers
    }

    override fun onResume() {
        super.onResume()
        val window = dialog.window
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, resources.getDimensionPixelSize(R.dimen.height_dialog_fragment))
        window.setGravity(Gravity.CENTER)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_close -> {
                dismiss()
            }
        }
    }

    fun setData(dayOfWeek: String, date: String){
        this.dayOfWeek = dayOfWeek
        this.date = date
    }

}