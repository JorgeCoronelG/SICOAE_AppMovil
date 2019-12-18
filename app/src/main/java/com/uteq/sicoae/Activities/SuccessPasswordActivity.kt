package com.uteq.sicoae.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.uteq.sicoae.LoginActivity
import com.uteq.sicoae.R

class SuccessPasswordActivity : AppCompatActivity(), View.OnClickListener {

    private var btnAccept: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_password)

        btnAccept = findViewById(R.id.btn_accept)
        btnAccept?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_accept -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {}
}
