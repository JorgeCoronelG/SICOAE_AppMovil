package com.uteq.sicoae.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast

import com.uteq.sicoae.R
import java.util.*

class CalendarFragment : Fragment(), CalendarView.OnDateChangeListener {

    private var calendar: CalendarView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        calendar = view.findViewById(R.id.calendar)
        calendar?.setOnDateChangeListener(this)

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.calendar)
    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        var dayOfWeek: String
        when(day){
            Calendar.MONDAY -> dayOfWeek = resources.getString(R.string.monday)
            Calendar.TUESDAY -> dayOfWeek = resources.getString(R.string.tuesday)
            Calendar.WEDNESDAY -> dayOfWeek = resources.getString(R.string.wednesday)
            Calendar.THURSDAY -> dayOfWeek = resources.getString(R.string.thursday)
            Calendar.FRIDAY -> dayOfWeek = resources.getString(R.string.friday)
            else -> dayOfWeek = ""
        }
        if(!dayOfWeek.isEmpty()){
            val registerStudent = RegisterStudentDialogFragment()
            registerStudent.setData(dayOfWeek, "${dayOfMonth} / ${month} / ${year}")
            registerStudent.isCancelable = false
            registerStudent.show(activity?.supportFragmentManager, registerStudent.TAG)
        }
    }
}