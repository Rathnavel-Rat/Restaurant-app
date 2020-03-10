package com.example.food.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.food.R
import com.example.food.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_rest.*


class RestActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigation: NavigationView
    lateinit var coordinator: CoordinatorLayout
    lateinit var FrameLayout: FrameLayout
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        navigation = findViewById<NavigationView>(R.id.navigation)
        coordinator = findViewById<CoordinatorLayout>(R.id.coordinate)
        FrameLayout = findViewById<FrameLayout>(R.id.framelayout)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        var previousItem: MenuItem? = null
        val et_search = findViewById<EditText>(R.id.et_search)
        setup()
        openRes()


        val drawerToggle =
            ActionBarDrawerToggle(this@RestActivity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigation.setNavigationItemSelectedListener {

            if (previousItem != null) {
                previousItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousItem = it

            when (it.itemId) {
                R.id.Res -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, RestarurantFragment()).addToBackStack("res")
                        .commit()
                    supportActionBar?.title = "Restraurants"
                    drawerLayout.closeDrawers()
                   toolbar.menu.findItem(R.id.scost).isVisible = true
                    toolbar.menu.findItem(R.id.srate).isVisible = true

                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, profileFragment()).addToBackStack("profile")
                        .commit()
                    supportActionBar?.title = "Profile"
                   toolbar.menu.findItem(R.id.scost).isVisible = false
                    toolbar.menu.findItem(R.id.srate).isVisible = false
                    drawerLayout.closeDrawers()
                }
                R.id.fav -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, FavouroteFragment()).addToBackStack("favourite")
                        .commit()
                    supportActionBar?.title = "Favourites"
                    toolbar.menu.findItem(R.id.scost).isVisible = false
                    toolbar.menu.findItem(R.id.srate).isVisible = false
                    drawerLayout.closeDrawers()
                }
                R.id.history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, HistoryFragment()).addToBackStack("histroy")
                        .commit()
                    supportActionBar?.title = "History"
                    toolbar.menu.findItem(R.id.scost).isVisible = false
                    toolbar.menu.findItem(R.id.srate).isVisible = false
                    drawerLayout.closeDrawers()
                }
                R.id.ques -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framelayout,
                        FaqFragment()
                    ).addToBackStack("faq").commit()
                    supportActionBar?.title = "FAQs"
                    toolbar.menu.findItem(R.id.scost).isVisible = false
                    toolbar.menu.findItem(R.id.srate).isVisible = false
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {
                }

            }
            return@setNavigationItemSelectedListener true
        }

    }


    fun setup() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Title"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)

    }

    fun openRes() {
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, RestarurantFragment())
            .addToBackStack("res").commit()
        supportActionBar?.title = "Restraurants"
        drawerLayout.closeDrawers()
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.framelayout)
        when (frag) {
            !is RestarurantFragment -> openRes()
            else -> super.onBackPressed()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        toolbar.inflateMenu(R.menu.menu_sorter)
        return super.onCreateOptionsMenu(menu)


    }
}
