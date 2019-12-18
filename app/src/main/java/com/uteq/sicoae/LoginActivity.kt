package com.uteq.sicoae

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.uteq.sicoae.activities.ForgotPasswordActivity
import com.uteq.sicoae.activities.HomeActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnSignIn: Button? = null
    private var txtForgot: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("section", R.id.nav_calendar);
                startActivity(intent)
                finish()
            }
            R.id.txt_forgot_password -> {
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
