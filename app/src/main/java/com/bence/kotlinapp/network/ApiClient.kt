package com.bence.kotlinapp.network

import com.bence.kotlinapp.dto.FixtureList
import com.bence.kotlinapp.dto.Standings
import com.bence.kotlinapp.utils.Constants.Companion.BASE_URL
import com.bence.kotlinapp.utils.Constants.Companion.TOKEN_HEADER_NAME
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("competitions/{league}/matches")
    fun getFixtureList(@Header(TOKEN_HEADER_NAME) token: String,
                       @Path("league") league: String,
                       @Query("matchday") matchDay: String
    ): Observable<FixtureList>

    @GET("competitions/{league}/standings?standingType=TOTAL")
    fun getLeagueTable(@Header(TOKEN_HEADER_NAME) token: String,
                       @Path("league") league: String
    ): Observable<Standings>

    companion object Factory {

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY })
            .build()

        fun create(): ApiClient {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiClient::class.java)
        }
    }
}