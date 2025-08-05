//package com.codepath.capstone
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import android.widget.FrameLayout
//import androidx.recyclerview.widget.RecyclerView // Also import RecyclerView for the recyclerView
//import androidx.core.view.WindowInsetsCompat
////imports for form inputs to work
//import android.widget.Button
//import com.google.android.material.textfield.TextInputEditText
//import com.google.android.material.slider.Slider
//import com.google.android.material.switchmaterial.SwitchMaterial
//import com.google.android.material.checkbox.MaterialCheckBox
//import com.google.android.material.chip.ChipGroup
//import com.google.android.material.chip.Chip
//// function for using those inputs inside below functions
//import android.view.View
//import androidx.recyclerview.widget.LinearLayoutManager
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//// importing our own objects
//import com.codepath.capstone.network.RetrofitInstance
//import com.codepath.capstone.model.LocalResult
//import android.util.Log // For logging errors
//
//private lateinit var resultsAdapter: ResultsAdapter
//private lateinit var searchView: View
//
//class MainActivity_Saurab : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        Log.d("MainActivity", "onCreate: Setting up initial view")
//        // Initially load the search form layout into the container
//        val container = findViewById<FrameLayout>(R.id.content_container)
//        val inflater = layoutInflater
//        val searchView = inflater.inflate(R.layout.search_component, container, false)
//        container.addView(searchView)
//
//        val searchButton: Button = searchView.findViewById(R.id.search_button)
//        searchButton.setOnClickListener {
//            Log.d("MainActivity", "Search button clicked")
//            // When clicked, swap to results layout
//            collectSearchInputs()
//            container.removeAllViews()
//            val resultsView = inflater.inflate(R.layout.results_layout, container, false)
//            container.addView(resultsView)
//        }
//    }
//
//    private fun setupListeners() {
//        val searchButton: Button = findViewById(R.id.search_button)
//        searchButton.setOnClickListener {
//            collectSearchInputs()
//        }
//    }
//
//    private fun collectSearchInputs() {
//        val query = searchView.findViewById<TextInputEditText>(R.id.search_term_edit_text)?.text?.toString() ?: ""
//        val location = searchView.findViewById<TextInputEditText>(R.id.city_edit_text)?.text?.toString() ?: ""
//        val minRating = searchView.findViewById<Slider>(R.id.rating_slider)?.value ?: 0f
//        val distance = searchView.findViewById<TextInputEditText>(R.id.distance_edit_text)?.text?.toString() ?: ""
//        val isOpenNow = searchView.findViewById<SwitchMaterial>(R.id.open_now_switch)?.isChecked ?: false
//        val isFamilyFriendly = searchView.findViewById<MaterialCheckBox>(R.id.family_friendly_checkbox)?.isChecked ?: false
//
//        val costChipGroup = searchView.findViewById<ChipGroup>(R.id.cost_chip_group)
//        val selectedChipId = costChipGroup?.checkedChipId ?: -1
//        val price = if (selectedChipId != -1) {
//            searchView.findViewById<Chip>(selectedChipId)?.text?.toString()
//        } else null
//
//        Log.d("MainActivity", "Inputs: query=$query, location=$location, minRating=$minRating, distance=$distance, openNow=$isOpenNow, price=$price, familyFriendly=$isFamilyFriendly")
//
//        fetchSearchResults(
//            query,
//            location,
//            minRating,
//            distance,
//            isOpenNow,
//            price,
//            isFamilyFriendly
//        )
//    }
//
//
//    private fun fetchSearchResults(
//        query: String,
//        location: String,
//        minRating: Float,
//        distance: String,
//        isOpenNow: Boolean,
//        price: String?,
//        isFamilyFriendly: Boolean
//    ) {
//        val apiKey = ""
//
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                Log.d("MainActivity", "Fetching results from API")
//                val response = RetrofitInstance.api.getPlaces(query, location, apiKey)
//                Log.d("MainActivity", "API call successful: ${'$'}{response.isSuccessful}")
//
//                val responseBody = response.body()
//                val results = responseBody?.local_results?.filter {
//                    (it.rating ?: 0.0) >= minRating &&
//                            (price == null || it.price_level == price)
//                } ?: emptyList()
//
//                Log.d("MainActivity", "Filtered ${'$'}{results.size} results")
//                withContext(Dispatchers.Main) {
//                    displaySearchResults(results)
//                }
//            } catch (e: Exception) {
//                Log.e("API_ERROR", "Error: ${e.message}", e)
//            }
//        }
//
//    }
//
//    private fun displaySearchResults(results: List<LocalResult>) {
//        Log.d("MainActivity", "Displaying ${'$'}{results.size} results")
//
//        val container = findViewById<FrameLayout>(R.id.content_container)
//        container.removeAllViews()
//        val inflater = layoutInflater
//        val resultsView = inflater.inflate(R.layout.results_layout, container, false)
//        container.addView(resultsView)
//
//        val recyclerView = resultsView.findViewById<RecyclerView>(R.id.resultsRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        if (!::resultsAdapter.isInitialized) {
//            resultsAdapter = ResultsAdapter(results.toMutableList())
//        } else {
//            resultsAdapter.updateData(results)
//        }
//        recyclerView.adapter = resultsAdapter
//
//        Log.d("MainActivity", "Adapter attached with ${'$'}{results.size} items")
//    }
//}