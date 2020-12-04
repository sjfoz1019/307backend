package com.example.adservermvp

import java.util.ArrayList

object Ads {

    var adList: MutableList<AdItem> = ArrayList()

//    init {
//        addItem(AdItem("Black Friday ads", "11/23/20", "www.google.com", "www.Google.com"))
//    }


    fun addItem(item: AdItem) {
//        ITEMS.addAll(item)
        adList.add(item)
    }

    fun clearItems(){
        adList.clear()
    }

    fun setItems(newItems: MutableList<AdItem>) {
        adList = newItems
    }

    data class AdItem(
        var mainText : String,
        var subText: String,
        var url: String,
        var imagePath: String
    ){
        override fun toString(): String = mainText + subText + url + imagePath
    }
}