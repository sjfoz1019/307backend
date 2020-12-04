package com.example.adservermvp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddAdActivity : AppCompatActivity() {

    lateinit var adSubmitButton: Button
    lateinit var adNameTextView: TextView
    lateinit var adSubTextView: TextView
    lateinit var adUrlTextView: TextView
    lateinit var adIPTextView: TextView

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

        Ads.addItem(
            AdItem(
                adName.toString(),
                adSub.toString(),
                adUrl.toString(),
                adIP.toString()
            )
        )
        //POST
        Toast.makeText(
            adSubmitButton.context,
            "you added Ad ${adName.toString()}",
            Toast.LENGTH_SHORT
        ).show()
    }
}
