package com.bence.kotlinapp.ui.base

abstract class BasePresenter<V: BaseView> {
    protected var view: V? = null

    fun attach(view: V) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}