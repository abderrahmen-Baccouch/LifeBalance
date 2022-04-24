package com.example.premierepage

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class PageAdmin : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_admin)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val imgMenu = findViewById<ImageView>(R.id.imgMenu)
        val navView = findViewById<NavigationView>(R.id.navDrawer_admin)
        navView.itemIconTintList = null
        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_sport -> {
                    Toast.makeText(applicationContext,"sport", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,GestionExerciceAdmin::class.java)
                startActivity(intent)}

                R.id.nav_repas -> {

                    Toast.makeText(applicationContext, "repas", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,GestionRepasAdmin::class.java)
                    startActivity(intent)
                }
                    /*R.id.nav_logout -> {
                    Toast.makeText(applicationContext,"Clicked LogOut", Toast.LENGTH_SHORT).show()

                }*/
                R.id.nav_aliments -> {
                    Toast.makeText(applicationContext,"aliments", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,GestionAlimentAdmin::class.java)
                    startActivity(intent)
                }

            }
            true
        }
    }
}