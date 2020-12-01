package com.example.adservermvp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdRecyclerViewAdapter(private var values: MutableList<Ads.AdItem>,
                              var clickListener: onAdClickListener)
    : RecyclerView.Adapter<MyAdRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ad_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(values.get(position), clickListener)
    }

    override fun getItemCount(): Int = values.size

    fun update(updatedList : MutableList<Ads.AdItem>){
        this.values = updatedList
        notifyItemInserted(values.size-1)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceType")
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val adNameView: TextView = view.findViewById(R.id.adName)

        fun initialize(value: Ads.AdItem, action:onAdClickListener){
            adNameView.text = value.adName
            itemView.setOnClickListener{
                action.onItemClick(value, adapterPosition)
            }
        }
    }

    interface onAdClickListener{
        fun onItemClick(value: Ads.AdItem, position: Int)
    }
}
