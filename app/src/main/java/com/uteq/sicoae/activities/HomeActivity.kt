package com.uteq.sicoae.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.uteq.sicoae.R
import com.uteq.sicoae.communication.CommunicationPath
import com.uteq.sicoae.controller.TokenController
import com.uteq.sicoae.controller.UserController
import com.uteq.sicoae.fragments.CalendarFragment
import com.uteq.sicoae.fragments.ProfileFragment
import com.uteq.sicoae.fragments.ReferenceFragment
import com.uteq.sicoae.fragments.StadisticsFragment
import com.uteq.sicoae.listener.DataListener
import dmax.dialog.SpotsDialog


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DataListener {

    companion object {
        const val STORAGE_PERMISSION_CODE = 1000
    }

    private var userController: UserController? = null
    private var tokenController: TokenController? = null
    private var dialog: android.app.AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.itemIconTintList = null
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        userController = UserController(this, this)
        tokenController = TokenController(this, this)
        tokenController?.update(tokenController!!.get())

        var intent = intent
        if(intent != null){
            val section = intent.getIntExtra("section", 0)
            navigationView.setCheckedItem(section)
            displaySelectedScreen(section)
        }

    }

    fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            //Ya se tiene el permiso
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            AlertDialog.Builder(this)
                .setTitle(resources.getString(R.string.required_permission))
                .setMessage(resources.getString(R.string.required_permission_text))
                .setPositiveButton(resources.getString(R.string.ok), { dialog, which ->
                    ActivityCompat.requestPermissions(this@HomeActivity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
                })
                .setNegativeButton(resources.getString(R.string.cancel), {dialog, which ->
                    dialog.dismiss()
                })
                .create().show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Permiso aceptado
            }else{
                Toast.makeText(this, resources.getString(R.string.denied_permission), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displaySelectedScreen(item.itemId)
        return true;
    }

    private fun displaySelectedScreen(itemId: Int) {
        var fragment: Fragment? = null
        when(itemId){
            R.id.nav_calendar -> fragment = CalendarFragment()
            R.id.nav_stadistics -> fragment = StadisticsFragment()
            R.id.nav_reference -> fragment = ReferenceFragment()
            R.id.nav_profile -> fragment = ProfileFragment()
            R.id.nav_logout -> {
                createDialog()
                userController?.logout()
            }
        }

        if(fragment != null){
            supportFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit()
        }

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun success(code: Int, obj: Object?) {
        when(code){
            CommunicationPath.UPDATE_TOKEN.index -> {
                //Toast.makeText(this, resources.getString(R.string.update_token_successful), Toast.LENGTH_SHORT).show()
                checkPermission()
            }
            CommunicationPath.LOGOUT.index -> {
                dialog?.dismiss()
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun error(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        checkPermission()
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
