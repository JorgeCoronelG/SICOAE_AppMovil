package com.uteq.sicoae.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.uteq.sicoae.LoginActivity
import com.uteq.sicoae.R
import com.uteq.sicoae.fragments.CalendarFragment
import com.uteq.sicoae.fragments.ProfileFragment
import com.uteq.sicoae.fragments.ReferenceFragment
import com.uteq.sicoae.fragments.StadisticsFragment


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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

        var intent = intent
        if(intent != null){
            val section = intent.getIntExtra("section", 0)
            navigationView.setCheckedItem(section)
            displaySelectedScreen(section)
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
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
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
}
