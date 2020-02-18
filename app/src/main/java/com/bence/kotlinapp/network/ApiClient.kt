package com.bence.kotlinapp.network

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class ApiClient {
    private val TOKEN_HEADER_NAME = "X-Auth-Token"
    private val TOKEN_HEADER_VALUE = ""
    private val BASE_URL = "https://api.football-data.org/v2"

    private var client = OkHttpClient()

    fun getFixtureList(callback: Callback, matchDay: String, league: String): Call {
        return getCall(callback, "$BASE_URL/competitions/$league/matches?matchday=$matchDay")
    }

    fun getLeagueTable(callback: Callback, league: String): Call {
        return getCall(callback,"$BASE_URL/competitions/$league/standings?standingType=TOTAL")
    }

    private fun getCall(callback: Callback, url: String): Call {
        val request = Request.Builder()
            .url(url)
            .addHeader(TOKEN_HEADER_NAME, TOKEN_HEADER_VALUE)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}