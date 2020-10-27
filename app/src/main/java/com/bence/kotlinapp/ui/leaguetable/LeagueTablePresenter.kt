package com.bence.kotlinapp.ui.leaguetable

import com.bence.kotlinapp.dto.Standings
import com.bence.kotlinapp.network.ApiClient
import com.bence.kotlinapp.ui.base.BasePresenter
import com.bence.kotlinapp.ui.base.BaseView
import com.bence.kotlinapp.utils.Constants.Companion.TOKEN_HEADER_VALUE
import com.bence.kotlinapp.utils.LeagueEnum
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LeagueTablePresenter : BasePresenter<LeagueTablePresenter.View>() {

    fun fetchLeagueTable(selectedLeague: LeagueEnum) {
        val apiService = ApiClient.create()

        addSubscription(apiService.getLeagueTable(TOKEN_HEADER_VALUE, selectedLeague.apiAbbreviation)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                view?.showLeagueTable(result)
            }, { error ->
                view?.showError()
                error.printStackTrace()
            }))
    }

    interface View : BaseView {
        fun showLeagueTable(standings: Standings)
    }
}