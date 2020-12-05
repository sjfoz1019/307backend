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
            Deferred<Unit>

    @PUT("campaigns/{campID}")
    fun putCampaigns(@Path("campID") campID: Int, @Body item: CampaignPost):
            Deferred<Unit>

    @DELETE("/campaigns/{campID}")
    fun deleteCampaign(@Path("campID") campID: Int):
            Deferred<Unit>

    @GET("/campaigns/{campID}/ads")
    fun getCampAds(@Path("campID") campID: Int):
            Deferred<List<AdItem>>

    @POST("/campaigns/{campID}/ads")
    fun postCampAds(@Path("campID") campID: Int, @Body ad: AdPost):
            Deferred<Unit>

    @PUT("/campaigns/{campID}/ads/{adID}")
    fun putAd(@Path("campID") campID: Int, @Path("adID") adID: Int, @Body ad: AdPost):
            Deferred<Unit>

    @DELETE("/campaigns/{campID}/ads/{adID}")
    fun deleteAd(@Path("campID") campID: Int, @Path("adID") adID: Int):
            Deferred<Unit>

    @DELETE("db")
    fun deleteCampaigns():
            Deferred<Unit>
}

object AdServerApi {
    val retrofitService: AdServerApiService by lazy {
        retrofit.create(AdServerApiService::class.java)
    }
}
