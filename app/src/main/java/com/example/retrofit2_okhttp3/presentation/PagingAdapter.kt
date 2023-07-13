package com.example.retrofit2_okhttp3.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.retrofit2_okhttp3.databinding.ItemRecyclerviewBinding
import com.example.retrofit2_okhttp3.domain.User

class PagingAdapter : PagingDataAdapter<User, RecyclerViewViewHolder>(object : DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val binding =
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewViewHolder(binding)
    }
}