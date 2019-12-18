package com.uteq.sicoae.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.uteq.sicoae.R
import com.uteq.sicoae.activities.ChangePasswordActivity
import com.uteq.sicoae.adapter.StudentAdapter
import com.uteq.sicoae.model.Student
import java.util.ArrayList

class ProfileFragment : Fragment() {

    private var recyclerStudents: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        recyclerStudents = view.findViewById(R.id.recycler_students)

        var manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerStudents?.layoutManager = manager
        val adapter = StudentAdapter(buildStudents(), R.layout.cardview_student, activity!!)
        recyclerStudents?.adapter = adapter

        setHasOptionsMenu(true)
        return view;
    }

    private fun buildStudents(): ArrayList<Student> {
        var students = ArrayList<Student>()
        students.add(Student(null,null,null,"Jorge Coronel González",null,null))
        students.add(Student(null,null,null,"Lucía Guadalupe Salinas Reyes",null,null))
        students.add(Student(null,null,null,"Estefanía Coronel González",null,null))
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
