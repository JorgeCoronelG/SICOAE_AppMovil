package com.uteq.sicoae.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.UserController
import com.uteq.sicoae.listener.DataListener
import dmax.dialog.SpotsDialog


class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener, DataListener {

    private var toolbar: Toolbar? = null
    private var btnRestore: Button? = null
    private var etEmail: EditText? = null
    private var dialog: AlertDialog? = null
    private var userController: UserController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        showToolbar(resources.getString(R.string.change_password), true)

        userController = UserController(this, this)

        btnRestore = findViewById(R.id.btn_restore)
        etEmail = findViewById(R.id.et_email)
        btnRestore?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_restore -> {
                if(etEmail?.text.toString().isEmpty()){
                    etEmail?.error = resources.getString(R.string.required_field)
                }else{
                    createDialog()
                    userController?.resetPassword(etEmail?.text.toString())
                }
            }
        }
    }

    override fun success(code: Int, obj: Object?) {
        dialog?.dismiss()
        when(code){
            CommunicationPath.RESET_PASSWORD.index -> {
                val intent = Intent(this, SuccessPasswordActivity::class.java)
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
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
