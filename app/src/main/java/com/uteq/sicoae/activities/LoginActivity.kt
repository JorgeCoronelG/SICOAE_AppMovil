package com.uteq.sicoae.activities

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.UserController
import com.uteq.sicoae.listener.DataListener
import dmax.dialog.SpotsDialog

class LoginActivity : AppCompatActivity(), View.OnClickListener, DataListener {

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnSignIn: Button? = null
    private var txtForgot: TextView? = null
    private var userController: UserController? = null
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userController = UserController(this, this)
        if(userController!!.isLogged()){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("section", R.id.nav_calendar);
            startActivity(intent)
            finish()
        }
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btn_sign_in)
        txtForgot = findViewById(R.id.txt_forgot_password)

        btnSignIn?.setOnClickListener(this)
        txtForgot?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_sign_in -> {
                if(checkFields()){
                    createDialog()
                    val correo = etEmail?.text.toString()
                    val clave = etPassword?.text.toString()
                    userController?.login(correo, clave)
                }
            }
            R.id.txt_forgot_password -> {
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    fun checkFields(): Boolean {
        var validation = true
        etEmail?.setError(null)
        etPassword?.setError(null)
        if(etEmail?.text.toString().isEmpty()) {
            etEmail?.error = resources.getString(R.string.required_email)
            validation = false
        }
        if(etPassword?.text.toString().isEmpty()) {
            etPassword?.error = resources.getString(R.string.required_password)
            validation = false
        }
        return validation
    }

    override fun success(code: Int, obj: Object?) {
        dialog?.dismiss()
        when(code){
            CommunicationPath.LOGIN.index -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("section", R.id.nav_calendar)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun error(error: String) {
        dialog?.dismiss()
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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