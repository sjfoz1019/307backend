package com.example.adservermvp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AdFrag : AppCompatActivity(), MyAdRecyclerViewAdapter.onAdClickListener {

    private var adAdapter = MyAdRecyclerViewAdapter(Ads.adList, this)
    private lateinit var addAdsButton: Button
    private lateinit var editCampaignButton: Button
    private lateinit var adRecyclerView: RecyclerView
    private val campaignJob = Job()
    private val coroutineScope = CoroutineScope(campaignJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab2_frag)

        addAdsButton = findViewById<Button>(R.id.addAdButton)
        editCampaignButton = findViewById<Button>(R.id.editCampaignButton)
        adRecyclerView = findViewById(R.id.adRecycleView)

        adRecyclerView.layoutManager = LinearLayoutManager(this)
        adRecyclerView.adapter = adAdapter

        refreshAds()

        // GET FOR AD LIST
        addAdsButton.setOnClickListener {
            val intent: Intent = Intent(this, AddAdActivity::class.java)
            val campaignid: Int = this.intent.extras?.getInt("campaignid") ?: -1
            intent.putExtra("campaignid", campaignid)
            startActivity(intent)
        }
        //moved this from before the onclicklistern to after (changed on 11/25)
        editCampaignButton.setOnClickListener {
            val intent: Intent = Intent(this, EditCampaignActivity::class.java)
            val campaignid: Int = this.intent.extras?.getInt("campaignid") ?: -1
            intent.putExtra("campaignid", campaignid)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
//        adAdapter.update(Ads.adList)
        refreshAds()
    }

    override fun onItemClick(value: AdItem, position: Int) {
        Toast.makeText(this, value.mainText, Toast.LENGTH_SHORT).show()
    }

    override fun onLongItemClick(value: AdItem, position: Int) {
        deleteAdById(value.id)
    }

    //Refreshing list after leaving home page and coming back to it
    private fun refreshAds() {
        coroutineScope.launch {
            val campaignid: Int = intent.extras?.getInt("campaignid") ?: -1
            var getAdsDeferred = AdServerApi.retrofitService.getCampAds(campaignid)
            try {
                var listResult = getAdsDeferred.await()
                Ads.setItems(listResult.toMutableList())
                Toast.makeText(applicationContext, "Success: ${listResult.size}", Toast.LENGTH_SHORT).show()
                adAdapter.update(Ads.adList)
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error loading campaigns: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Deleting a singular campaign by item long click
    private fun deleteAdById(value:Int){
        coroutineScope.launch {
            val campaignid: Int = intent.extras?.getInt("campaignid") ?: -1
            var deleteAdDeferred = AdServerApi.retrofitService.deleteAd(campaignid, value)
            try {
                var result = deleteAdDeferred.await()
                Toast.makeText(applicationContext, "Success: ${result}", Toast.LENGTH_SHORT).show()
            } catch (t: Throwable) {
                Toast.makeText(applicationContext, "Error delete campaigns: ${t.message}", Toast.LENGTH_SHORT).show()
            }
            refreshAds()
        }
    }
}