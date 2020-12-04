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

class AddAdActivity : AppCompatActivity() {

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
        val adName = adNameTextView.text
        val adSub = adSubTextView.text
        val adUrl = adUrlTextView.text
        val adIP = adIPTextView.text
        val campaignid: Int = this.intent.extras?.getInt("campaignid") ?: -1
        val newAd = AdPost(
            adName.toString(),
            adSub.toString(),
            adUrl.toString(),
            adIP.toString()
        )

        coroutineScope.launch {
            val postCampaignsDeferred = AdServerApi.retrofitService.postCampAds(campaignid, newAd)
            try {
                val result = postCampaignsDeferred.await()
                Toast.makeText(applicationContext, "Success: $result", Toast.LENGTH_SHORT).show()
            } catch (t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error submitting ad: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
