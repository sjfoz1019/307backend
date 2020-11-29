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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab2_frag)

        val addAds : Button = findViewById<Button>(R.id.addAdButton)
        val adRecycleView : RecyclerView = findViewById(R.id.adRecycleView)

        adRecycleView.layoutManager = LinearLayoutManager(this)
        adRecycleView.adapter = adAdapter
        // GET FOR AD LIST
        addAds.setOnClickListener {
            startActivity(Intent(this, AddAdActivity::class.java))
        }
        //moved this from before the onclicklistern to after (changed on 11/25)

    }

    override fun onResume() {
        super.onResume()
        adAdapter.update(Ads.adList)
    }

    override fun onItemClick(value: Ads.AdItem, position: Int) {
        Toast.makeText(this, value.adName, Toast.LENGTH_SHORT).show()
    }
}