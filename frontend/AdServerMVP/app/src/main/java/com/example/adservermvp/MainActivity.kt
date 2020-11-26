package com.example.adservermvp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MyItemRecyclerViewAdapter.onCampaignClickListener {

    private var campaignAdapter = MyItemRecyclerViewAdapter(Campaign.ITEMS, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addCampaign: Button = findViewById<Button>(R.id.addAdButton)
        val campaignRecycleView: RecyclerView = findViewById<RecyclerView>(R.id.campaignRecycleView)

        campaignRecycleView.layoutManager = LinearLayoutManager(this)
        campaignRecycleView.adapter = campaignAdapter
        //GET REQUEST for campaign list
        campaignAdapter.update(Campaign.ITEMS)

        addCampaign.setOnClickListener{
            startActivity(Intent(this, AddCampaignActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        //GET REQUEST
        campaignAdapter.update(Campaign.ITEMS)
    }

    override fun onItemClick(value: Campaign.CampaignItem, position: Int) {
        startActivity(Intent(this, AdFrag::class.java))
    }
}