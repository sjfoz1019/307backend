package com.example.adservermvp

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

// On emulator, 10.0.2.2 points to the host machine
private const val BASE_URL = "http://10.0.2.2:8080/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface AdServerApiService {
    @GET("campaigns")
    fun getCampaigns():
            Deferred<List<CampaignItem>>

    @POST("campaigns")
    fun postCampaigns(@Body item: CampaignPost):
            Deferred<String>

    @PUT("campaigns/{campID}")
    fun putCampaigns(@Path("campID") campID: Int, @Body item: CampaignPost):
            Deferred<String>

    @DELETE("/campaigns/{campID}")
    fun deleteCampaign(@Path("campID") campID: Int):
            Deferred<String>

    @GET("/campaigns/{campId}/ads")
    fun getCampAds(@Path ("campId") campId: Int):
            Deferred<List<Ads.AdItem>>

    @DELETE("/campaigns/{campID}/ads/{adID}")
    fun deleteAd(@Path("campId") campID: Int, @Path("adID") adID: Int):
            Deferred<String>

    @DELETE("db")
    fun deleteCampaigns():
            Deferred<String>
}

object AdServerApi {
    val retrofitService : AdServerApiService by lazy {
        retrofit.create(AdServerApiService::class.java)
    }
}
