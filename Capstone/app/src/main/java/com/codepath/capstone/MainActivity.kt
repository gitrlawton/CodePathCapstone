package com.codepath.capstone

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val apiKey = "13a395d09e47268c9895eb8684cafac24b4e9dc9749058c9884a9f4b4e416b12"
        val query = "restaurants"
        val location = "New York, NY"

        val url = "https://serpapi.com/search.json?engine=google_local&query=$query&location=$location&api_key=$apiKey"

        Log.d("API_KEY", "API Key: $apiKey")
        }
    }