package com.uteq.sicoae.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.uteq.sicoae.R
import com.uteq.sicoae.adapter.ReferenceAdapter
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.ReferenceController
import com.uteq.sicoae.controller.TutorController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.model.Reference
import com.uteq.sicoae.model.Student
import com.uteq.sicoae.model.Tutor
import dmax.dialog.SpotsDialog

class HistoricalReferencesActivity : AppCompatActivity(), ReferenceAdapter.OnReferenceListener, DataListener {

    private var toolbar: Toolbar? = null
    private var recyclerReference: RecyclerView? = null
    private var imgLoading: ImageView? = null
    private var llHistorical: LinearLayout? = null
    private var llEmpty: LinearLayout? = null
    private var students: ArrayList<Student>? = null
    private var referenceController: ReferenceController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historical_references)
        showToolbar(resources.getString(R.string.historical), true)

        recyclerReference = findViewById(R.id.recycler_references)
        imgLoading = findViewById(R.id.img_loading)
        llHistorical = findViewById(R.id.ll_historical)
        llEmpty = findViewById(R.id.ll_empty)

        referenceController = ReferenceController(this, this)

        referenceController?.get(TutorController(this, null).getId())
    }

    override fun success(code: Int, obj: Object?) {
        when(code){
            CommunicationPath.GET_REFERENCES.index -> {
                imgLoading?.visibility = View.GONE
                if(obj != null){
                    llHistorical?.visibility = View.VISIBLE

                    val tutor = obj as Tutor
                    students = tutor.estudiantes

                    var manager = LinearLayoutManager(this)
                    manager.orientation = LinearLayoutManager.VERTICAL
                    recyclerReference?.layoutManager = manager
                    val adapter = ReferenceAdapter(tutor.estudiantes!!, R.layout.cardview_reference,this, this)
                    recyclerReference?.adapter = adapter
                }else{
                    llEmpty?.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun error(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun onReferenceClick(position: Int) {
        val student = students!!.get(position)
        var intent = Intent(this, EditReferenceActivity::class.java)
        intent.putExtra("reference", student.reference?.id)
        intent.putExtra("student", student?.nombre)
        intent.putExtra("person", student.reference?.persona)
        startActivity(intent)
        finish()
    }

    private fun showToolbar(title: String, upButton: Boolean) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(upButton)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("section", R.id.nav_reference)
        startActivity(intent)
        finish()
    }
}
