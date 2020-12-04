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

class EditCampaignActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var campaignNameTextView: TextView
    lateinit var campaignStartDateTextView: TextView
    lateinit var campaignEndDateTextView: TextView

    private val campaignJob = Job()
    private val coroutineScope = CoroutineScope(campaignJob + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_campaign)

        submitButton = findViewById(R.id.submitButton)
        campaignNameTextView = findViewById(R.id.editTextCampaign)
        campaignStartDateTextView = findViewById(R.id.textStartDate)
        campaignEndDateTextView = findViewById(R.id.textEndDate)

        submitButton.setOnClickListener {
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        val campaignid: Int = getIntent().extras?.getInt("campaignid") ?: -1
        val campaignName = campaignNameTextView.text
        val campaignStartDate = campaignStartDateTextView.text
        val campaignEndDate = campaignEndDateTextView.text
        val campaign = CampaignPost(
            campaignName.toString(),
            campaignStartDate.toString(),
            campaignEndDate.toString()
        )

        coroutineScope.launch {
            var putCampaignsDeferred =
                AdServerApi.retrofitService.putCampaigns(campaignid, campaign)
            try {
                var result = putCampaignsDeferred.await()
                Toast.makeText(applicationContext, "Success: $result", Toast.LENGTH_SHORT).show()
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
