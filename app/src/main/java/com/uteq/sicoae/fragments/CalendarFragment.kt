package com.uteq.sicoae.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast

import com.uteq.sicoae.R

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
        var date = "${dayOfMonth} / ${month} / ${year}"
        Toast.makeText(context, date, Toast.LENGTH_SHORT).show()
    }
}
