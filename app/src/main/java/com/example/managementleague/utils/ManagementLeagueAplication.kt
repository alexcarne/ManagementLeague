package com.example.managementleague.utils

import android.app.Application

class ManagementLeagueAplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Locator.initWith(this)
    }
}