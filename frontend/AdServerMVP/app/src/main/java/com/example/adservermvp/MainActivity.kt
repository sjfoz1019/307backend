package com.example.adservermvp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MyItemRecyclerViewAdapter.onCampaignClickListener {

    private var campaignAdapter = MyItemRecyclerViewAdapter(Campaign.ITEMS, this)
    private val campaignJob = Job()
    private val coroutineScope = CoroutineScope(campaignJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addCampaign: Button = findViewById<Button>(R.id.addAdButton)
        val deleteCampaignBut: Button = findViewById<Button>(R.id.deleteCampaignButton)
        val campaignRecycleView: RecyclerView = findViewById<RecyclerView>(R.id.campaignRecycleView)

        campaignRecycleView.layoutManager = LinearLayoutManager(this)
        campaignRecycleView.adapter = campaignAdapter

        refreshCampaigns()
        campaignAdapter.update(Campaign.ITEMS)

        addCampaign.setOnClickListener{
            startActivity(Intent(this, AddCampaignActivity::class.java))
        }

        deleteCampaignBut.setOnClickListener {
            deleteCampaigns()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshCampaigns()
    }

    override fun onItemClick(value: CampaignItem, position: Int) {
        startActivity(Intent(this, AdFrag::class.java))
    }

    private fun refreshCampaigns() {
        coroutineScope.launch {
            var getCampaignsDeferred = AdServerApi.retrofitService.getCampaigns()
            try {
                var listResult = getCampaignsDeferred.await()
                Campaign.setItems(listResult.toMutableList())
                Toast.makeText(applicationContext, "Success: ${listResult.size}", Toast.LENGTH_SHORT).show()
                campaignAdapter.update(Campaign.ITEMS)
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error loading campaigns: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteCampaigns(){
        coroutineScope.launch {
            var deleteCampaignsDeferred = AdServerApi.retrofitService.deleteCampaigns()
            try {
                var result = deleteCampaignsDeferred.await()
                Toast.makeText(applicationContext, "Success: ${result}", Toast.LENGTH_SHORT).show()
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error delete campaigns: ${t.message}", Toast.LENGTH_SHORT).show()
            }
            refreshCampaigns()
        }
    }
}