package com.bence.kotlinapp.ui.fixturelist

import com.bence.kotlinapp.utils.LeagueEnum
import com.bence.kotlinapp.dto.FixtureList
import com.bence.kotlinapp.network.ApiClient
import com.bence.kotlinapp.ui.base.BasePresenter
import com.bence.kotlinapp.ui.base.BaseView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.*

class FixtureListPresenter : BasePresenter<FixtureListPresenter.View>() {

    fun fetchFixtureList(selectedLeague: LeagueEnum, selectedMatchDay: String) {
        val request = ApiClient()

        request.getFixtureList(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                view?.showError()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                val fixtureList: FixtureList? = parseFixtureList(responseData)
                if (fixtureList != null) {
                    view?.showFixtureList(fixtureList)
                }
            }
        }, selectedMatchDay, selectedLeague.apiAbbreviation)
    }

    interface View : BaseView {
        fun showFixtureList(fixtureList: FixtureList)
    }

    private fun parseFixtureList(fixturesResponse: String?) : FixtureList? {
        val moshiInstance: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
        val adapter: JsonAdapter<FixtureList> = moshiInstance.adapter(FixtureList::class.java)
        return adapter.fromJson(fixturesResponse!!)
    }
}