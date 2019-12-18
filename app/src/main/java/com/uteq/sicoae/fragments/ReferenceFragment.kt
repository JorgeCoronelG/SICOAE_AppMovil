package com.uteq.sicoae.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.uteq.sicoae.activities.HistoricalReferencesActivity

import com.uteq.sicoae.R
import com.uteq.sicoae.activities.ReferenceActivity

class ReferenceFragment : Fragment(), View.OnClickListener {

    private var spnStudent: Spinner? = null
    private val students = arrayOf("Selecciona:")
    private var btnGenerate: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reference, container, false)

        spnStudent = view.findViewById(R.id.spn_student)
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, students)
        spnStudent?.adapter = arrayAdapter
        btnGenerate = view.findViewById(R.id.btn_generate)
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
                val intent = Intent(activity, ReferenceActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
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

}