package com.example.premierepage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class aliments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aliments)

        var viewPager = findViewById(R.id.viewPager) as ViewPager
    var tabLayout = findViewById(R.id.tablayout) as TabLayout

        val fragmentAdapter = FragmentAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(FoodFragment(),"REPAS")
       fragmentAdapter.addFragment(NotreRepas(),"NOTRE REPAS")
        fragmentAdapter.addFragment(AlimentsFragment(),"MES ALIMENTS")
        fragmentAdapter.addFragment(NotreAlimentsFragment(),"NOTRE ALIMENTS")


       viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

    }
}