package com.example.adservermvp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddCampaignActivity : AppCompatActivity() {

    lateinit var submitButton: Button
    lateinit var campaignNameTextView: TextView
    lateinit var campaignStartDateTextView: TextView
    lateinit var campaignEndDateTextView: TextView

    lateinit var pickStartDateButton: Button
    lateinit var pickEndDateButton: Button

    private val campaignJob = Job()
    private val coroutineScope = CoroutineScope(campaignJob + Dispatchers.Main)

    // Internal start and end date stored in epoch seconds
    private var startDate: Long = 0
    private var endDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_campaign)

        submitButton = findViewById<Button>(R.id.submitButtonAd)
        campaignNameTextView = findViewById<TextView>(R.id.editTextCampaign)
        campaignStartDateTextView = findViewById<TextView>(R.id.textStartDate)
        campaignEndDateTextView = findViewById<TextView>(R.id.textEndDate)

        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        val startDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                startDate = cal.timeInMillis / 1000
                campaignStartDateTextView.text = sdf.format(cal.time)
            }

        val endDateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                endDate = cal.timeInMillis / 1000
                campaignEndDateTextView.text = sdf.format(cal.time)
            }

        submitButton.setOnClickListener { submitCampaign() }
        campaignStartDateTextView.setOnClickListener {
            DatePickerDialog(
                this@AddCampaignActivity, startDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        campaignEndDateTextView.setOnClickListener {
            DatePickerDialog(
                this@AddCampaignActivity, endDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun submitCampaign() {
        val campaignName = campaignNameTextView.text
        val campaign = CampaignPost(
            campaignName.toString(),
            startDate,
            endDate
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
