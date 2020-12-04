package com.example.adservermvp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdFrag : AppCompatActivity(), MyAdRecyclerViewAdapter.onAdClickListener {

    private var adAdapter = MyAdRecyclerViewAdapter(Ads.adList, this)
    private lateinit var addAdsButton: Button
    private lateinit var editCampaignButton: Button
    private lateinit var adRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab2_frag)

        addAdsButton = findViewById<Button>(R.id.addAdButton)
        editCampaignButton = findViewById<Button>(R.id.editCampaignButton)
        adRecyclerView = findViewById(R.id.adRecycleView)

        adRecyclerView.layoutManager = LinearLayoutManager(this)
        adRecyclerView.adapter = adAdapter
        // GET FOR AD LIST
        addAdsButton.setOnClickListener {
            val intent: Intent = Intent(this, AddAdActivity::class.java)
            val campaignid: Int = getIntent().extras?.getInt("campaignid") ?: -1
            intent.putExtra("campaignid", campaignid)
            startActivity(Intent(this, AddAdActivity::class.java))
        }
        //moved this from before the onclicklistern to after (changed on 11/25)
        editCampaignButton.setOnClickListener {
            val intent: Intent = Intent(this, EditCampaignActivity::class.java)
            val campaignid: Int = getIntent().extras?.getInt("campaignid") ?: -1
            intent.putExtra("campaignid", campaignid)
            deleteAds()
        }
    }

    override fun onResume() {
        super.onResume()
        adAdapter.update(Ads.adList)
    }

    override fun onItemClick(value: Ads.AdItem, position: Int) {
        Toast.makeText(this, value.mainText, Toast.LENGTH_SHORT).show()
    }

    private fun deleteAds() {
        Ads.clearItems()
        adAdapter.update(Ads.adList)
    }
}