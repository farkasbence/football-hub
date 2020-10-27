package com.bence.kotlinapp.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V: BaseView> {
    protected var view: V? = null
    private val subscription = CompositeDisposable()

    fun attach(view: V) {
        this.view = view
    }

    fun addSubscription(disposable: Disposable) {
        subscription.add(disposable)
    }

    fun detachView() {
        subscription.dispose()
        this.view = null
    }
}