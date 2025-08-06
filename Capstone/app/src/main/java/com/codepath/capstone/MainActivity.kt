package com.codepath.capstone

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.capstone.model.LocalResult
import com.codepath.capstone.network.RetrofitInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.Slider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var resultsAdapter: ResultsAdapter
    private lateinit var searchView: View

    //***** main activity container ******
    private lateinit var container: FrameLayout
    //***** main activity container ******

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<View>(R.id.main)

        //******************************************************
        //**** nav bar and main activity container *************
        val bottomNavBar =
            findViewById<BottomNavigationView>(R.id.bottomNavBar) //changed this line to use BottomNavigationView. gives setOnItemSelectedListener method to bottomNavBar -Alex
        container = findViewById(R.id.fragment_container)
        //**** nav bar and main activity container *************
        //******************************************************

        //***********************************
        //***** code for the nav bar ********

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
        //***** code for the nav bar *******
        //**********************************

        //***********************************************************************
        //*** code for giving functionality to the nav bar WORK IN PROGRESS -Alex
        loadLayout(R.layout.place_item)

        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadLayout(R.layout.place_item)
                    true
                }

                R.id.nav_search -> {
                    loadSearchForm()
                    true
                }

                else -> false
            }
        }

        //********************************************************************
        //**********Code for the button to work when when ever we click on it

    }

    private fun loadLayout(layoutResId: Int) {
        container.removeAllViews()
        val view = LayoutInflater.from(this).inflate(layoutResId, container, false)
        container.addView(view)
    }

    //*** End of nav bar functionality code block ******
    //**************************************************

    //******Loading the Search layout******************
    //**************************************************
    private fun loadSearchForm() {
        container.removeAllViews()
        searchView = LayoutInflater.from(this).inflate(R.layout.search_component, container, false)
        container.addView(searchView)

        val searchButton = searchView.findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            collectSearchInputs()
        }
    }
    //************End of Loading the Search Layout******
    //**************************************************

    //************Beginning of API**********************
    //**************************************************

    // collecting search results
    private fun collectSearchInputs() {
        val query =
            searchView.findViewById<TextInputEditText>(R.id.search_term_edit_text)?.text?.toString()
                ?: ""
        val location =
            searchView.findViewById<TextInputEditText>(R.id.city_edit_text)?.text?.toString() ?: ""
        val minRating = searchView.findViewById<Slider>(R.id.rating_slider)?.value ?: 0f
        val distance =
            searchView.findViewById<TextInputEditText>(R.id.distance_edit_text)?.text?.toString()
                ?: ""
        val isOpenNow =
            searchView.findViewById<SwitchMaterial>(R.id.open_now_switch)?.isChecked ?: false
        val isFamilyFriendly =
            searchView.findViewById<MaterialCheckBox>(R.id.family_friendly_checkbox)?.isChecked
                ?: false

        val costChipGroup = searchView.findViewById<ChipGroup>(R.id.cost_chip_group)
        val selectedChipId = costChipGroup?.checkedChipId ?: -1
        val price = if (selectedChipId != -1) {
            searchView.findViewById<Chip>(selectedChipId)?.text?.toString()
        } else null

        Log.d(
            "MainActivity",
            "Inputs: query=$query, location=$location, minRating=$minRating, distance=$distance, openNow=$isOpenNow, price=$price, familyFriendly=$isFamilyFriendly"
        )

        fetchSearchResults(
            query,
            location,
            minRating.toDouble(),
            distance,
            isOpenNow,
            price,
            isFamilyFriendly
        )
    }
    private fun fetchSearchResults(
        query: String,
        location: String,
        minRating: Double,       // Use Double to match data class rating type
        distance: String,        // Not used yet, can be implemented with API params or filtering
        isOpenNow: Boolean,
        price: String?,          // E.g. "$" or "$$"
        isFamilyFriendly: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getPlaces(
                    query = query,
                    location = location,
                    hl = "en",
                    gl = "us",
                    domain = "google.com",
                    apiKey = BuildConfig.API_KEY
                )

                if (response.isSuccessful) {
                    val body = response.body()
                    val places = body?.local_results?.places ?: emptyList()

                    // Debug log
                    Log.d("API_CALL", "Places found: ${places.size}")

                    val filteredPlaces = places.filter { place ->

                        // Filter by rating - ensure rating is not null and meets minimum
                        val meetsRating = (place.rating ?: 0.0) >= minRating.toDouble()

                        // Filter by price if provided - adapt field name if needed
                        val meetsPrice = price?.let { it == place.price_level || it == place.price } ?: true

                        // Filter by open now - simplistic check based on hours string containing "Open"
                        val meetsOpenNow = if (isOpenNow) {
                            place.hours?.contains("Open", ignoreCase = true) == true
                        } else true

                        // Filter by family friendly - you might want to check category/type keywords
                        val meetsFamilyFriendly = if (isFamilyFriendly) {
                            place.category?.contains("family", ignoreCase = true) == true ||
                                    place.category?.contains("kid", ignoreCase = true) == true ||
                                    place.category?.contains("children", ignoreCase = true) == true
                        } else true

                        meetsRating && meetsPrice && meetsOpenNow && meetsFamilyFriendly
                    }

                    withContext(Dispatchers.Main) {
                        displaySearchResults(filteredPlaces)
                    }

                } else {
                    val errorJson = response.errorBody()?.string()
                    Log.e("API_CALL", "API error code: ${response.code()}, error: $errorJson")
                    withContext(Dispatchers.Main) {
                        displaySearchResults(emptyList())
                    }
                }

            } catch (e: Exception) {
                Log.e("API_ERROR", "Exception during API call: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    displaySearchResults(emptyList())
                }
            }
        }
    }


    // display the results
    private fun displaySearchResults(results: List<LocalResult>) {
        container.removeAllViews()
        val resultsView = LayoutInflater.from(this).inflate(R.layout.results_layout, container, false)
        container.addView(resultsView)

        val recyclerView = resultsView.findViewById<RecyclerView>(R.id.resultsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (!::resultsAdapter.isInitialized) {
            resultsAdapter = ResultsAdapter(results.toMutableList())
        } else {
            resultsAdapter.updateData(results)
        }

        recyclerView.adapter = resultsAdapter
    }
}
