package com.bence.kotlinapp.ui.fixturelist

import com.bence.kotlinapp.dto.FixtureList
import com.bence.kotlinapp.network.ApiClient
import com.bence.kotlinapp.ui.base.BasePresenter
import com.bence.kotlinapp.ui.base.BaseView
import com.bence.kotlinapp.utils.Constants.Companion.TOKEN_HEADER_VALUE
import com.bence.kotlinapp.utils.LeagueEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FixtureListPresenter : BasePresenter<FixtureListPresenter.View>() {

    fun fetchFixtureList(selectedLeague: LeagueEnum, selectedMatchDay: String) {
        val apiService = ApiClient.create()
        addSubscription(apiService.getFixtureList(TOKEN_HEADER_VALUE, selectedLeague.apiAbbreviation, selectedMatchDay)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                view?.showFixtureList(result)
            }, { error ->
                view?.showError()
                error.printStackTrace()
            }))
    }

    interface View : BaseView {
        fun showFixtureList(fixtureList: FixtureList)
    }
}