package com.example.adservermvp

import java.util.ArrayList

// Don't send an id when making a new ad.
data class AdPost(
    var mainText : String,
    var subText: String,
    var url: String,
    var imagePath: String
)

data class AdItem(
    var id: Int,
    var mainText : String,
    var subText: String,
    var url: String,
    var imagePath: String
){
    override fun toString(): String = mainText + subText + url + imagePath
}

object Ads {

    var adList: MutableList<AdItem> = ArrayList()

    fun addItem(item: AdItem) {
        adList.add(item)
    }

    fun clearItems(){
        adList.clear()
    }

    fun setItems(newItems: MutableList<AdItem>) {
        adList = newItems
    }
}