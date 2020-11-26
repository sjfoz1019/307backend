package com.example.adservermvp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

/**
 * [RecyclerView.Adapter] that can display a [CampaignItem].
 * */
class MyItemRecyclerViewAdapter(
        private var values: MutableList<Campaign.CampaignItem>,
        var clickListener: onCampaignClickListener)
    : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.campaign_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(values.get(position), clickListener)
    }

    override fun getItemCount(): Int = values.size

    fun update(updatedList : MutableList<Campaign.CampaignItem>){
        this.values = updatedList
        notifyItemInserted(values.size-1)
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceType")
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.campaignName)
        val dateView: TextView = view.findViewById(R.id.campaignStartDate)

        fun initialize(value: Campaign.CampaignItem, action:onCampaignClickListener){
            idView.text = value.name
            dateView.text = value.startDate

            itemView.setOnClickListener{
                action.onItemClick(value, adapterPosition)
            }
        }
    }

    interface onCampaignClickListener{
        fun onItemClick(value: Campaign.CampaignItem, position: Int)
    }

}