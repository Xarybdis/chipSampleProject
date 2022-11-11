package com.example.android_chipsampleapp.ui.breed_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_chipsampleapp.R
import com.example.android_chipsampleapp.databinding.ListItemBreedBinding


class BreedListAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<BreedListAdapter.BreedListViewHolder>() {
    private var breedNameList = mutableListOf<Pair<String, List<String>>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedListViewHolder =
        BreedListViewHolder(ListItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: BreedListViewHolder, position: Int) {
        holder.bind(breedNameList[position])
    }

    override fun getItemCount(): Int = breedNameList.size

    fun updateBreedList(list: List<Pair<String, List<String>>>) {
        breedNameList.clear()
        breedNameList.addAll(list)
        notifyDataSetChanged()
    }

    fun updateListAccordingToSearch(searchValue: String) {

        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun breedItemClickListener(breedName: String)
        fun subItemClickListener(breedName: String, subBreedName: String)
    }

    inner class BreedListViewHolder(private val itemBinding: ListItemBreedBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(breedItem: Pair<String, List<String>>) {
            itemBinding.apply {
                breedName.text = breedItem.first.replaceFirstChar { it.uppercaseChar() }

                itemBinding.innerLayout.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.breedItemClickListener(breedItem.first)
                    }
                }

                if (breedItem.second.isNotEmpty()) {
                    itemBinding.imageViewExpandSubItems.isVisible = true
                    itemBinding.imageViewExpandSubItems.setOnClickListener {
                        itemBinding.recyclerviewSubBreedList.isVisible = !itemBinding.recyclerviewSubBreedList.isVisible
                        itemBinding.imageViewExpandSubItems.setImageResource(if (itemBinding.recyclerviewSubBreedList.isVisible) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
                    }

                    val subBreedAdapter = BreedSubListAdapter(breedItem.first, breedItem.second, listener)
                    recyclerviewSubBreedList.layoutManager = LinearLayoutManager(itemView.context)
                    recyclerviewSubBreedList.adapter = subBreedAdapter
                } else {
                    itemBinding.imageViewExpandSubItems.isVisible = false
                    itemBinding.recyclerviewSubBreedList.isVisible = false
                }
            }
        }
    }
}
