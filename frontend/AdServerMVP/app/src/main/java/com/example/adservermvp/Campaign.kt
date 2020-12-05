package com.example.adservermvp

import java.util.ArrayList

// We don't send an id when creating a new campaign.
data class CampaignPost(
    var name: String,
    var startDate: Long,
    var endDate: Long
)

data class CampaignItem(
    var id: Int,
    var name: String,
    var startDate: Long,
    var endDate: Long
) {
    override fun toString(): String = name + startDate
}

object Campaign {

    /**
     * An array of campaign items.
     */
    var ITEMS: MutableList<CampaignItem> = ArrayList()

    init {
    }

    fun addItem(item: CampaignItem) {
        ITEMS.add(item)
    }

    fun clearItems() {
        ITEMS.clear()
    }

    fun setItems(newItems: MutableList<CampaignItem>) {
        ITEMS = newItems
    }
}