package com.example.listmaker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
val listTitles = arrayOf("Shopping List", "Chores", "Android Tutorials")
class ListSelectionRecyclerViewAdapter : RecyclerView.Adapter<ListSelectionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {

    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return listTitles.size
    }

}