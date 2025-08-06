package com.codepath.capstone

import android.content.Intent
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
import androidx.core.widget.NestedScrollView
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

    private lateinit var container: FrameLayout
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var searchView: View
    private lateinit var resultsAdapter: ResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val restaurantRecyclerView = findViewById<RecyclerView>(R.id.restaurantRecyclerView)

        val restaurantList = listOf(
            Restaurant("Delhi Biryani", "Indian Cuisine", R.drawable.biryani),
            Restaurant("Shinjuku Sushi Blast", "Japanese Cuisine", R.drawable.sushiplate),
            Restaurant("Back to Rome", "Italian Cuisine", R.drawable.pizzaplace),
            Restaurant("Griddle House", "American Cuisine", R.drawable.burgerjoint),
            Restaurant("Taco Diaries", "Mexican Cuisine", R.drawable.tacospot)
        )

        val restaurantAdapter = RestaurantAdapter(restaurantList)
        restaurantRecyclerView.layoutManager = LinearLayoutManager(this)
        restaurantRecyclerView.adapter = restaurantAdapter

        val mainLayout = findViewById<View>(R.id.main)
        container = findViewById(R.id.fragment_container)
        bottomNavBar = findViewById(R.id.bottomNavBar)

        // Search pill click listener to open SearchActivity
        val searchPillContainer = findViewById<FrameLayout>(R.id.searchPillContainer)
        searchPillContainer.setOnClickListener {
            loadSearchForm()
            bottomNavBar.selectedItemId = R.id.nav_search
        }

        // Handle window insets
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

        // Nav Bar Clicks
        loadHomeLayout()
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadHomeLayout()
                    true
                }
                R.id.nav_search -> {
                    loadSearchForm()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadHomeLayout() {
        findViewById<NestedScrollView>(R.id.scrollContainer).visibility = View.VISIBLE
        container.visibility = View.GONE
        container.removeAllViews()
    }

    private fun loadSearchForm() {
        findViewById<NestedScrollView>(R.id.scrollContainer).visibility = View.GONE
        container.visibility = View.VISIBLE
        container.removeAllViews()

        searchView = LayoutInflater.from(this).inflate(R.layout.search_component, container, false)
        container.addView(searchView)

        val searchButton = searchView.findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            collectSearchInputs()
        }
    }

    private fun collectSearchInputs() {
        val query = searchView.findViewById<TextInputEditText>(R.id.search_term_edit_text)?.text?.toString() ?: ""
        val location = searchView.findViewById<TextInputEditText>(R.id.city_edit_text)?.text?.toString() ?: ""
        val minRating = searchView.findViewById<Slider>(R.id.rating_slider)?.value ?: 0f
        val distance = searchView.findViewById<TextInputEditText>(R.id.distance_edit_text)?.text?.toString() ?: ""
        val isOpenNow = searchView.findViewById<SwitchMaterial>(R.id.open_now_switch)?.isChecked ?: false
        val isFamilyFriendly = searchView.findViewById<MaterialCheckBox>(R.id.family_friendly_checkbox)?.isChecked ?: false

        val costChipGroup = searchView.findViewById<ChipGroup>(R.id.cost_chip_group)
        val selectedChipId = costChipGroup?.checkedChipId ?: -1
        val price = if (selectedChipId != -1) {
            searchView.findViewById<Chip>(selectedChipId)?.text?.toString()
        } else null

        fetchSearchResults(query, location, minRating.toDouble(), distance, isOpenNow, price, isFamilyFriendly)
    }

    private fun fetchSearchResults(
        query: String,
        location: String,
        minRating: Double,
        distance: String,
        isOpenNow: Boolean,
        price: String?,
        isFamilyFriendly: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getPlaces(
                    query = query,
                    location = location,
                    num = 20,
                    hl = "en",
                    gl = "us",
                    domain = "google.com",
                    apiKey = BuildConfig.API_KEY
                )

                if (response.isSuccessful) {
                    val places = response.body()?.local_results ?: emptyList()
                    withContext(Dispatchers.Main) {
                        displaySearchResults(places)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        displaySearchResults(emptyList())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    displaySearchResults(emptyList())
                }
            }
        }
    }

    private fun displaySearchResults(results: List<LocalResult>) {
        findViewById<NestedScrollView>(R.id.scrollContainer).visibility = View.GONE
        container.visibility = View.VISIBLE
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