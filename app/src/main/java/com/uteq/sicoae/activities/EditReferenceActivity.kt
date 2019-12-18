package com.uteq.sicoae.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.EditText
import com.uteq.sicoae.R

class EditReferenceActivity : AppCompatActivity() {

    private var etReference: EditText? = null
    private var etStudent: EditText? = null
    private var etPerson: EditText? = null
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reference)
        showToolbar(resources.getString(R.string.reference), true)

        etReference = findViewById(R.id.et_reference)
        etStudent = findViewById(R.id.et_student)
        etPerson = findViewById(R.id.et_person)

        val intent = getIntent()
        if(intent != null){
            etReference?.setText(intent.getStringExtra("reference"))
            etStudent?.setText(intent.getStringExtra("student"))
            etPerson?.setText(intent.getStringExtra("person"))
        }
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
        super.onBackPressed()
        finish()
    }
}
