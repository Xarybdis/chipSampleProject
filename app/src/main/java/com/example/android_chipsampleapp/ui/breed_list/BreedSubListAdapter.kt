package com.example.android_chipsampleapp.ui.breed_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_chipsampleapp.databinding.ListItemSubBreedBinding


class BreedSubListAdapter(
    private var breedSupType: String,
    private var breedSubTypeList: List<String>,
    private val subListener: BreedListAdapter.OnItemClickListener
) : RecyclerView.Adapter<BreedSubListAdapter.BreedSubListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedSubListViewHolder =
        BreedSubListViewHolder(ListItemSubBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BreedSubListViewHolder, position: Int) {
        holder.bind(breedSubTypeList[position])
    }

    override fun getItemCount(): Int = breedSubTypeList.size

    inner class BreedSubListViewHolder(private val itemBinding: ListItemSubBreedBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(subBreedName: String) {
            itemBinding.subBreedName.text = subBreedName

            itemBinding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        subListener.subItemClickListener(breedSupType, subBreedName)
                    }
                }
            }
        }
    }
}
