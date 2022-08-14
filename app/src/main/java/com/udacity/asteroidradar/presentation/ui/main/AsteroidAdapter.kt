package com.udacity.asteroidradar.presentation.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import com.udacity.asteroidradar.pojo.Asteroid

class AsteroidAdapter(val onClickListener: OnClickListener):ListAdapter<Asteroid,RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAsteroidBinding.inflate(inflater, parent, false)
        return AsteroidViewHolder(binding)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as AsteroidViewHolder
        val asteroid = getItem(position)
        viewHolder.itemView.setOnClickListener {
            onClickListener.onClick(asteroid)
        }
        viewHolder.bind(asteroid)
    }
    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

    class AsteroidViewHolder(private val binding: ItemAsteroidBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asteroid) {
            binding.asteroid = item
            binding.executePendingBindings()
        }
    }
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

}