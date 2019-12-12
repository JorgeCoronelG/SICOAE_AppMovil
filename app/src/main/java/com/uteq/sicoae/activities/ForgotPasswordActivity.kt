package com.uteq.sicoae.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import com.uteq.sicoae.LoginActivity
import com.uteq.sicoae.R


class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private var toolbar: Toolbar? = null
    private var btnRestore: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        showToolbar(resources.getString(R.string.change_password), true)

        btnRestore = findViewById(R.id.btn_restore)
        btnRestore?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_restore -> {
                val intent = Intent(this, SuccessPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showToolbar(title: String, upButton: Boolean) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(upButton)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed();
        return true;
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
