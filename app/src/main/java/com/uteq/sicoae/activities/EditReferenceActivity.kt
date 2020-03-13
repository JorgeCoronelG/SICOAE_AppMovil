package com.uteq.sicoae.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.ReferenceController
import com.uteq.sicoae.listener.DataListener
import dmax.dialog.SpotsDialog

class EditReferenceActivity : AppCompatActivity(), View.OnClickListener, DataListener {

    private var txtReference: TextView? = null
    private var txtStudent: TextView? = null
    private var etPerson: EditText? = null
    private var btnSave: Button? = null
    private var toolbar: Toolbar? = null
    private var dialog: AlertDialog? = null
    private var referenceController: ReferenceController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reference)
        showToolbar(resources.getString(R.string.reference), true)

        txtReference = findViewById(R.id.txt_reference)
        txtStudent = findViewById(R.id.txt_student)
        etPerson = findViewById(R.id.et_person)
        btnSave = findViewById(R.id.btn_save)

        referenceController = ReferenceController(this, this)

        btnSave?.setOnClickListener(this)

        val intent = getIntent()
        if(intent != null){
            txtReference?.setText(intent.getStringExtra("reference"))
            txtStudent?.setText(intent.getStringExtra("student"))
            etPerson?.setText(intent.getStringExtra("person"))
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_save -> {
                if(checkField()){
                    createDialog()
                    val id = txtReference?.text.toString()
                    val persona = etPerson?.text.toString()
                    referenceController?.update(id, persona)
                }
            }
        }
    }

    fun checkField(): Boolean{
        etPerson?.setError(null)
        if(etPerson?.text.toString().isEmpty()){
            etPerson?.setError(resources.getString(R.string.required_field))
            return false
        }else{
            return true
        }
    }

    override fun success(code: Int, obj: Object?) {
        dialog?.dismiss()
        when(code){
            CommunicationPath.UPDATE_REFERENCE.index -> {
                Toast.makeText(this, resources.getString(R.string.update_reference_succesful), Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    onBackPressed()
                }, 2500)
            }
        }
    }

    override fun error(error: String) {
        dialog?.dismiss()
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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
        val intent = Intent(this, HistoricalReferencesActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun createDialog(){
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(resources.getString(R.string.wait_a_moment))
            .setCancelable(false)
            .build()
        dialog!!.show()
    }
}