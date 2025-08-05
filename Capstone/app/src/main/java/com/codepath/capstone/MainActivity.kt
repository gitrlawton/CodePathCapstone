package com.codepath.capstone

import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
<<<<<<< HEAD
    //***** main activity container ******
    private lateinit var container: FrameLayout
    //***** main activity container ******
=======
    private lateinit var container: FrameLayout
>>>>>>> 738c8d8d83bf432a3ba5bdacf42563bc85efdb23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<View>(R.id.main)
<<<<<<< HEAD

        //******************************************************
        //**** nav bar and main activity container *************
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavBar) //changed this line to use BottomNavigationView. gives setOnItemSelectedListener method to bottomNavBar -Alex
        container = findViewById(R.id.fragment_container)
        //**** nav bar and main activity container *************
        //******************************************************

        //***********************************
        //***** code for the nav bar ********
=======
        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavBar) //changed this line to use BottomNavigationView. gives setOnItemSelectedListener method to bottomNavBar -Alex
        container = findViewById(R.id.fragment_container)

>>>>>>> 738c8d8d83bf432a3ba5bdacf42563bc85efdb23
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(bottomNavBar) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }
<<<<<<< HEAD
        //***** code for the nav bar *******
        //**********************************

        //***********************************************************************
        //*** code for giving functionality to the nav bar WORK IN PROGRESS -Alex
=======
    //*** code for giving functionality to the nav bar WORK IN PROGRESS -Alex
>>>>>>> 738c8d8d83bf432a3ba5bdacf42563bc85efdb23
        loadLayout(R.layout.activity_main)

        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.nav_home -> {
                    loadLayout(R.layout.activity_main)
                    true
                }
                R.id.nav_search -> {
                    loadLayout(R.layout.search_component)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadLayout(layoutResId: Int){
        container.removeAllViews()
        val view = LayoutInflater.from(this).inflate(layoutResId, container, false)
        container.addView(view)
    }

    //*** End of nav bar functionality code block ******
<<<<<<< HEAD
    //**************************************************
}
=======
}
>>>>>>> 738c8d8d83bf432a3ba5bdacf42563bc85efdb23
