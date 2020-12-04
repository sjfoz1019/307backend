package com.example.adservermvp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EditCampaignActivity : AppCompatActivity() {
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_campaign)

        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            handleSubmit()
        }
    }

    private fun handleSubmit() {
//TODO
    }
}
