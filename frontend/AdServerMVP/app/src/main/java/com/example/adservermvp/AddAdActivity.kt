package com.example.adservermvp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddAdActivity : AppCompatActivity() {

    lateinit var adSubmitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_ads)

        adSubmitButton = findViewById(R.id.submitButtonAd)

        adSubmitButton.setOnClickListener {
            var adNameTextView = findViewById<TextView>(R.id.textAdName)
            var adFormatTextView = findViewById<TextView>(R.id.textFormat)
            var adSizeTextView = findViewById<TextView>(R.id.adSize)
            var adUrlTextView = findViewById<TextView>(R.id.landingURL)

            var adName = adNameTextView.text
            var adFormat = adFormatTextView.text
            var adSize = adSizeTextView.text
            var adUrl = adUrlTextView.text

            Ads.addItem(Ads.AdItem(adName.toString(), adFormat.toString(), adSize.toString().toInt(), adUrl.toString()))
            //POST
            Toast.makeText(adSubmitButton.context, "you added Ad ${adName.toString()}", Toast.LENGTH_SHORT).show()
        }
    }
}