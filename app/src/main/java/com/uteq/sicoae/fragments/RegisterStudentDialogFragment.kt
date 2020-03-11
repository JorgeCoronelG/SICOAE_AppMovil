package com.uteq.sicoae.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.*
import android.widget.*
import com.uteq.sicoae.adapter.RegisterStudentAdapter
import com.uteq.sicoae.model.Register
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.RegisterController
import com.uteq.sicoae.controller.TutorController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.model.Tutor

class RegisterStudentDialogFragment : DialogFragment(), View.OnClickListener, DataListener {

    private var btnClose: Button? = null
    private var txtDay: TextView? = null
    private var txtDate: TextView? = null
    private var imgLoading: ImageView? = null
    private var dayOfWeek: String? = null
    private var date: String? = null
    private var recyclerStudents: RecyclerView? = null
    private var registerController: RegisterController? = null

    val TAG = RegisterStudentDialogFragment::class.simpleName

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(R.layout.dialog_fragment_student, container, false)

        btnClose = view.findViewById(R.id.btn_close)
        txtDay = view.findViewById(R.id.txt_day)
        txtDate = view.findViewById(R.id.txt_date)
        imgLoading = view.findViewById(R.id.img_loading)
        recyclerStudents = view.findViewById(R.id.recycler_students)

        registerController = RegisterController(context!!, this)
        txtDay?.text = dayOfWeek
        txtDate?.text = date

        registerController?.getDayRegister(TutorController(context!!, null).getId(), date!!)
        btnClose?.setOnClickListener(this)

        return view
    }

    override fun success(code: Int, obj: Object?) {
        when(code){
            CommunicationPath.GET_DAY_REGISTER.index -> {
                imgLoading?.visibility = View.GONE
                recyclerStudents?.visibility = View.VISIBLE
                val tutor = obj as Tutor
                var manager = LinearLayoutManager(context)
                manager.orientation = LinearLayoutManager.VERTICAL
                recyclerStudents?.layoutManager = manager
                val adapter = RegisterStudentAdapter(tutor.estudiantes!!, R.layout.cardview_register_student, activity!!)
                recyclerStudents?.adapter = adapter
            }
        }
    }

    override fun error(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
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