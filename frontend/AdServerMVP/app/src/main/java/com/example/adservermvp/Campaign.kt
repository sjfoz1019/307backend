package com.example.adservermvp
import java.util.ArrayList
import java.util.HashMap

object Campaign {

    /**
     * An array of campaign items.
     */
    var ITEMS: MutableList<CampaignItem> = ArrayList()

    init {
        addItem(CampaignItem("Black Friday ads", "11/23/20", "11/30/20"))
    }

    fun addItem(item: CampaignItem) {
//        ITEMS.addAll(item)
        ITEMS.add(item)
    }

    open fun clearItems(){
        ITEMS.clear()
    }

    data class CampaignItem(
        var name : String,
        var startDate: String,
        var endDate: String
    ){
        override fun toString(): String = name + startDate
    }
}