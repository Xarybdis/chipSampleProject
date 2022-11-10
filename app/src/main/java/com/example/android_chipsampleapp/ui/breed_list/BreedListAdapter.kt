package com.example.android_chipsampleapp.ui.breed_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_chipsampleapp.databinding.ListItemBreedBinding


class BreedListAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<BreedListAdapter.BreedListViewHolder>() {
    private var breedTypeList = mutableListOf<Pair<String, List<String>>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedListViewHolder =
        BreedListViewHolder(ListItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: BreedListViewHolder, position: Int) {
        holder.bind(breedTypeList[position])
    }

    override fun getItemCount(): Int = breedTypeList.size

    fun updateBreedList(list: List<Pair<String, List<String>>>) {
        breedTypeList.clear()
        breedTypeList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun breedItemClickListener(breedName: String)
        fun subItemClickListener(breedName: String, subBreedName: String)
    }

    inner class BreedListViewHolder(private val itemBinding: ListItemBreedBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(breedItem: Pair<String, List<String>>) {
            itemBinding.breedName.text = breedItem.first

            itemBinding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.breedItemClickListener(breedItem.first)
                    }
                }
            }

            if (breedItem.second.isNotEmpty()) {
                val subBreedAdapter = BreedSubListAdapter(breedItem.first, breedItem.second, listener)
                itemBinding.recyclerviewSubBreedList.layoutManager = LinearLayoutManager(itemView.context)
                itemBinding.recyclerviewSubBreedList.adapter = subBreedAdapter
            }
        }
    }
}
