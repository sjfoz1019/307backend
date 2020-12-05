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

class EditCampaignActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var campaignNameTextView: TextView
    lateinit var campaignStartDateTextView: TextView
    lateinit var campaignEndDateTextView: TextView

    private val campaignJob = Job()
    private val coroutineScope = CoroutineScope(campaignJob + Dispatchers.Main)

    // Internal start and end date stored in epoch seconds, same as AddCampaignActivity
    private var startDate: Long = 0
    private var endDate: Long = 0

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

        campaignStartDateTextView.setOnClickListener {
            DatePickerDialog(
                this@EditCampaignActivity, startDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        campaignEndDateTextView.setOnClickListener {
            DatePickerDialog(
                this@EditCampaignActivity, endDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun handleSubmit() {
        val campaignid: Int = getIntent().extras?.getInt("campaignid") ?: -1
        val campaignName = campaignNameTextView.text
        val campaign = CampaignPost(
            campaignName.toString(),
            startDate,
            endDate
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
