package com.uteq.sicoae.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import com.uteq.sicoae.R
import com.uteq.sicoae.adapter.ReferenceAdapter
import com.uteq.sicoae.model.Reference
import com.uteq.sicoae.model.Student

class HistoricalReferencesActivity : AppCompatActivity(), ReferenceAdapter.OnReferenceListener {

    private var toolbar: Toolbar? = null
    private var recyclerReference: RecyclerView? = null
    private var references: ArrayList<Reference>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historical_references)
        showToolbar(resources.getString(R.string.historical), true)

        recyclerReference = findViewById(R.id.recycler_references)

        var manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerReference?.layoutManager = manager
        buildReferences()
        val adapter = ReferenceAdapter(references, R.layout.cardview_reference, this, this)
        recyclerReference?.adapter = adapter

    }

    private fun buildReferences() {
        references = ArrayList<Reference>()
        references?.add(Reference("20191125LMF83901",null,null,"María Martínez Álvarez",
            Student(null,null,null,"Jorge Coronel González",null,null)))
        references?.add(Reference("20191125LMF83902",null,null,"María Martínez Álvarez",
            Student(null,null,null,"Lucía Guadalupe Salinas Reyes",null,null)))
        references?.add(Reference("20191125LMF83903",null,null,"María Martínez Álvarez",
            Student(null,null,null,"Estefanía Coronel González",null,null)))
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

    override fun onReferenceClick(position: Int) {
        val reference = references?.get(position)
        var intent = Intent(this, EditReferenceActivity::class.java)
        intent.putExtra("reference", reference?.id)
        intent.putExtra("student", reference?.student?.nombre)
        intent.putExtra("person", reference?.persona)
        startActivity(intent)
    }
}
