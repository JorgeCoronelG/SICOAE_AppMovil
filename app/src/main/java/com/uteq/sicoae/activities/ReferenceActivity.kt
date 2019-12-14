package com.uteq.sicoae.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.uteq.sicoae.R

class ReferenceActivity : AppCompatActivity(), View.OnClickListener {

    var llShare: LinearLayout? = null
    var llCapture: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reference)

        llShare = findViewById(R.id.ll_share)
        llCapture = findViewById(R.id.ll_capture)

        llShare?.setOnClickListener(this)
        llCapture?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_share -> {
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            }
            R.id.ll_capture -> {
                Toast.makeText(this, "Capture", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("section", R.id.nav_reference)
        startActivity(intent)
        finish()
    }
}
