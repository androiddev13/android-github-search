package com.githubsearch.di

import com.githubsearch.AndroidApplication
import com.githubsearch.di.component.DaggerAppComponent

object AppInjector {
    fun init(application: AndroidApplication) {
        DaggerAppComponent.builder().application(application)
            .build().inject(application)
    }
}