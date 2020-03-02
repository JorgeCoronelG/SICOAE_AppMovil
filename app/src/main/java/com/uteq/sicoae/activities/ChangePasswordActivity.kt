package com.uteq.sicoae.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.UserController
import com.uteq.sicoae.listener.DataListener
import com.uteq.sicoae.model.User
import dmax.dialog.SpotsDialog

class ChangePasswordActivity : AppCompatActivity(), DataListener, View.OnClickListener {

    private var toolbar: Toolbar? = null
    private var correo: String? = null
    private var userController: UserController? = null
    private var dialog: AlertDialog? = null
    private var etOldPassword: EditText? = null
    private var etNewPassword: EditText? = null
    private var btnChange: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        showToolbar(resources.getString(R.string.change_password), true)
        userController = UserController(this, this)
        etOldPassword = findViewById(R.id.et_old_password)
        etNewPassword = findViewById(R.id.et_new_password)
        btnChange = findViewById(R.id.btn_change)

        btnChange?.setOnClickListener(this)

        val intent = getIntent()
        if(intent != null){
            correo = intent.getStringExtra("correo")
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_change -> {
                if(checkFields()){
                    createDialog()
                    var user = User(correo, etOldPassword?.text.toString())
                    userController?.changePassword(user, etNewPassword?.text.toString())
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        var validation = true
        etOldPassword?.setError(null)
        etNewPassword?.setError(null)
        if(etOldPassword?.text.toString().isEmpty()) {
            etOldPassword?.error = resources.getString(R.string.required_field)
            validation = false
        }
        if(etNewPassword?.text.toString().isEmpty()) {
            etNewPassword?.error = resources.getString(R.string.required_field)
            validation = false
        }
        if(etNewPassword!!.length() < 6){
            etNewPassword?.error = resources.getString(R.string.required_password_lenght)
            validation = false
        }
        return validation
    }

    override fun success(code: Int, obj: Object?) {
        dialog?.dismiss()
        when(code){
            CommunicationPath.CHANGE_PASSWORD.index -> {
                Toast.makeText(this, resources.getString(R.string.update_password_succesful), Toast.LENGTH_SHORT).show()
                etOldPassword?.setText("")
                etNewPassword?.setText("")
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
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("section", R.id.nav_profile)
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
