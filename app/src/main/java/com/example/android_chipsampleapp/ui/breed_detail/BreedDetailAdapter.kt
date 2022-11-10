package com.example.android_chipsampleapp.ui.breed_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_chipsampleapp.databinding.ListItemBreedImageBinding

class BreedDetailAdapter(private val context: Context) : RecyclerView.Adapter<BreedDetailAdapter.BreedDetailViewHolder>() {
    private val breedImageList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedDetailViewHolder =
        BreedDetailViewHolder(ListItemBreedImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BreedDetailViewHolder, position: Int) {
        holder.bind(breedImageList[position])
    }

    override fun getItemCount(): Int = breedImageList.size

    fun updateBreedImageList(list: List<String>) {
        breedImageList.clear()
        breedImageList.addAll(list)
        notifyDataSetChanged()
    }

    inner class BreedDetailViewHolder(private val itemBinding: ListItemBreedImageBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(breedImageUrl: String) {
            Glide.with(context).load(breedImageUrl).into(itemBinding.imageviewDetail)
        }
    }
}