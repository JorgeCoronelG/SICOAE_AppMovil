package com.uteq.sicoae.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.*

import com.uteq.sicoae.R
import com.uteq.sicoae.activities.ChangePasswordActivity
import com.uteq.sicoae.adapter.StudentAdapter
import com.uteq.sicoae.model.Student
import java.util.ArrayList

class ProfileFragment : Fragment() {

    private var recyclerStudents: androidx.recyclerview.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        recyclerStudents = view.findViewById(R.id.recycler_students)

        var manager = androidx.recyclerview.widget.LinearLayoutManager(context)
        manager.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
        recyclerStudents?.layoutManager = manager
        val adapter = StudentAdapter(buildStudents(), R.layout.cardview_student, activity!!)
        recyclerStudents?.adapter = adapter

        setHasOptionsMenu(true)
        return view;
    }

    private fun buildStudents(): ArrayList<Student> {
        var students = ArrayList<Student>()
        students.add(Student(null,null,"Jorge Coronel González",1,"A",null))
        students.add(Student(null,null,"Lucía Guadalupe Salinas Reyes",2,"B",null))
        students.add(Student(null,null,"Estefanía Coronel González",3,"C",null))
        return students
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
                val intent = Intent(context, ChangePasswordActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
