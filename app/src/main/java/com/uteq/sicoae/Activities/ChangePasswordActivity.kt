package com.uteq.sicoae.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.uteq.sicoae.R

class ChangePasswordActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        showToolbar(resources.getString(R.string.change_password), true)
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
}
