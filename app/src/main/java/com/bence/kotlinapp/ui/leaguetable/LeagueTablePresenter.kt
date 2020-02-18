package com.bence.kotlinapp.ui.leaguetable

import com.bence.kotlinapp.dto.Standings
import com.bence.kotlinapp.network.ApiClient
import com.bence.kotlinapp.ui.base.BasePresenter
import com.bence.kotlinapp.ui.base.BaseView
import com.bence.kotlinapp.utils.LeagueEnum
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LeagueTablePresenter(apiClient: ApiClient) : BasePresenter<LeagueTablePresenter.View>() {

    private val mApiClient = apiClient

    fun fetchLeagueTable(selectedLeague: LeagueEnum) {

        mApiClient.getLeagueTable(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                view?.showError()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                val standings = parseStandings(responseData)

                if (standings != null) {
                    view?.showLeagueTable(standings)
                }
            }
        }, selectedLeague.apiAbbreviation)
    }

    interface View : BaseView {
        fun showLeagueTable(standings: Standings)
    }

    private fun parseStandings(standingResponse: String?) : Standings? {
        val moshiInstance: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter: JsonAdapter<Standings> = moshiInstance.adapter(Standings::class.java)
        return adapter.fromJson(standingResponse!!)
    }
}