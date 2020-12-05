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

    //coming back to home screen
    override fun onResume() {
        super.onResume()
        refreshCampaigns()
    }

    //Selecting a campaign to see what ads exist in selected campaign
    override fun onItemClick(value: CampaignItem, position: Int) {
        val intent: Intent = Intent(this, AdFrag::class.java)
        intent.putExtra("campaignid", value.id)
        startActivity(intent)
    }

    override fun onLongItemClick(value: CampaignItem, position: Int) {
        deleteCampaignById(value.id)
    }

    //Refreshing list after leaving home page and coming back to it
    private fun refreshCampaigns() {
        coroutineScope.launch {
            val getCampaignsDeferred = AdServerApi.retrofitService.getCampaigns()
            try {
                val listResult = getCampaignsDeferred.await()
                Campaign.setItems(listResult.toMutableList())
                campaignAdapter.update(Campaign.ITEMS)
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error loading campaigns: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Deleting all campaigns through "delete campaign" button click
    private fun deleteCampaigns(){
        coroutineScope.launch {
            val deleteCampaignsDeferred = AdServerApi.retrofitService.deleteCampaigns()
            try {
                deleteCampaignsDeferred.await()
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error delete campaigns: ${t.message}", Toast.LENGTH_SHORT).show()
            }
            refreshCampaigns()
        }
    }

    //Deleting a singular campaign by item long click
    private fun deleteCampaignById(value:Int){
        coroutineScope.launch {
            val deleteCampaignsDeferred = AdServerApi.retrofitService.deleteCampaign(value)
            try {
                deleteCampaignsDeferred.await()
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error delete campaigns: ${t.message}", Toast.LENGTH_SHORT).show()
            }
            refreshCampaigns()
        }
    }
}