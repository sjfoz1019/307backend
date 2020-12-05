package com.example.adservermvp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditAdActivity : AppCompatActivity() {
    lateinit var adSubmitButton: Button
    lateinit var adNameTextView: TextView
    lateinit var adSubTextView: TextView
    lateinit var adUrlTextView: TextView
    lateinit var adIPTextView: TextView

    private val campaignJob = Job()
    private val coroutineScope = CoroutineScope(campaignJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ads)

        adSubmitButton = findViewById(R.id.submitButtonAd)
        adNameTextView = findViewById<TextView>(R.id.textAdName)
        adSubTextView = findViewById<TextView>(R.id.subText)
        adUrlTextView = findViewById<TextView>(R.id.url)
        adIPTextView = findViewById<TextView>(R.id.imagePath)

        adSubmitButton.setOnClickListener {
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        val campaignid: Int = getIntent().extras?.getInt("campaignid") ?: -1
        val adId: Int = getIntent().extras?.getInt("adId") ?: -1
        val adName = adNameTextView.text
        val adSub = adSubTextView.text
        val adUrl = adUrlTextView.text
        val adIP = adIPTextView.text
        val newAd = AdPost(
                adName.toString(),
                adSub.toString(),
                adUrl.toString(),
                adIP.toString()
        )

        coroutineScope.launch {
            var putCampaignsDeferred =
                AdServerApi.retrofitService.putAd(campaignid, adId, newAd)
            try {
                putCampaignsDeferred.await()
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
            } catch (t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error editing campaign: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
