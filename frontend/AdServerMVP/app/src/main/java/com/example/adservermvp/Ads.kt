package com.example.adservermvp

import java.util.ArrayList

object Ads {

    var adList: MutableList<AdItem> = ArrayList()

    init {
        addItem(AdItem("Black Friday ads", "11/23/20", 20, "www.Google.com"))
    }


    fun addItem(item: AdItem) {
//        ITEMS.addAll(item)
        adList.add(item)
    }

    fun clearItems(){
        adList.clear()
    }

    data class AdItem(
        var adName : String,
        var formatType: String,
        var adSize : Int,
        var landingUrl: String
    ){
        override fun toString(): String = adName
    }
}