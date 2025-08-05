package com.codepath.capstone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.capstone.model.LocalResult

class ResultsAdapter(private val items: MutableList<LocalResult>) :
    RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val locationName: TextView = view.findViewById(R.id.locationName)
        val starRating: TextView = view.findViewById(R.id.starRating)
        val hoursOfOperation: TextView = view.findViewById(R.id.hoursOfOperation)
        val description: TextView = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = items[position]

        holder.locationName.text = item.title ?: "Unknown Place"
        val rating = item.rating ?: 0.0
        val stars = "★".repeat(rating.toInt()) + "☆".repeat(5 - rating.toInt())
        holder.starRating.text = "$stars (${item.reviews ?: 0})"
        holder.hoursOfOperation.text = item.hours ?: "Hours not available"
        holder.description.text = item.category ?: item.address ?: "No description"

        if (!item.thumbnail.isNullOrEmpty()) {
            Glide.with(holder.image.context)
                .load(item.thumbnail)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder_image)
        }
    }
    // update method instead of creating a new adapter every time.
    fun updateData(newItems: List<LocalResult>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}
