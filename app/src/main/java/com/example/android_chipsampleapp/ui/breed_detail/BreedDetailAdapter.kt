package com.example.android_chipsampleapp.ui.breed_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_chipsampleapp.databinding.ListItemBreedImageBinding

class BreedDetailAdapter(private val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<BreedDetailAdapter.BreedDetailViewHolder>() {
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

    interface OnItemClickListener {
        fun breedImageItemClick(breedImageUrl: String)
    }

    inner class BreedDetailViewHolder(private val itemBinding: ListItemBreedImageBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(breedImageUrl: String) {
            itemBinding.apply {
                Glide.with(context).load(breedImageUrl).into(imageviewDetail)

                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.breedImageItemClick(breedImageUrl)
                    }
                }
            }
        }
    }
}