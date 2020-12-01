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
            handleSubmit()
        }
    }

    private fun handleSubmit(){
        var adNameTextView = findViewById<TextView>(R.id.textAdName)
        var adSubTextView = findViewById<TextView>(R.id.subText)
        var adUrlTextView = findViewById<TextView>(R.id.url)
        var adIPTextView = findViewById<TextView>(R.id.imagePath)

        var adName = adNameTextView.text
        var adSub = adSubTextView.text
        var adUrl = adUrlTextView.text
        var adIP = adIPTextView.text

        Ads.addItem(Ads.AdItem(adName.toString(), adSub.toString(), adUrl.toString(), adIP.toString()))
        //POST
        Toast.makeText(adSubmitButton.context, "you added Ad ${adName.toString()}", Toast.LENGTH_SHORT).show()
    }
}