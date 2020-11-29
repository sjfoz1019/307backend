package com.example.adservermvp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddCampaignActivity: AppCompatActivity() {

    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_campaign)

        submitButton = findViewById<Button>(R.id.submitButtonAd)

        submitButton.setOnClickListener {
            var campaignNameTextView = findViewById<TextView>(R.id.editTextCampaign)
            var campaignStartDateTextView = findViewById<TextView>(R.id.textStartDate)
            var campaignEndDateTextView = findViewById<TextView>(R.id.textEndDate)

            var campaignName = campaignNameTextView.text
            var campaignStartDate = campaignStartDateTextView.text
            var campaignEndDate = campaignEndDateTextView.text

            Campaign.addItem(CampaignItem(campaignName.toString(), campaignStartDate.toString(), campaignEndDate.toString()))
            //POST
            Toast.makeText(submitButton.context, "you added campaign # ${campaignName.toString()}", Toast.LENGTH_SHORT).show()
        }
    }
}