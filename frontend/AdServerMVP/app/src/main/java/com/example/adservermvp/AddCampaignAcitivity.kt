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

class AddCampaignActivity : AppCompatActivity() {

    lateinit var submitButton: Button
    lateinit var campaignNameTextView: TextView
    lateinit var campaignStartDateTextView: TextView
    lateinit var campaignEndDateTextView: TextView

    private val campaignJob = Job()
    private val coroutineScope = CoroutineScope(campaignJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_campaign)

        submitButton = findViewById<Button>(R.id.submitButtonAd)
        campaignNameTextView = findViewById<TextView>(R.id.editTextCampaign)
        campaignStartDateTextView = findViewById<TextView>(R.id.textStartDate)
        campaignEndDateTextView = findViewById<TextView>(R.id.textEndDate)

        submitButton.setOnClickListener { submitCampaign() }
    }

    private fun submitCampaign() {
        val campaignName = campaignNameTextView.text
        val campaignStartDate = campaignStartDateTextView.text
        val campaignEndDate = campaignEndDateTextView.text
        val campaign = CampaignPost(
            campaignName.toString(),
            campaignStartDate.toString(),
            campaignEndDate.toString()
        )

        coroutineScope.launch {
            val postCampaignsDeferred = AdServerApi.retrofitService.postCampaigns(campaign)
            try {
                postCampaignsDeferred.await()
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
            } catch (t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error submitting campaign: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
