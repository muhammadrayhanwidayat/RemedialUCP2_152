package com.example.remedialucp2_152

import android.app.Application
import com.example.remedialucp2_152.repository.AppContainer
import com.example.remedialucp2_152.repository.ContainerApp

class LibraryApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ContainerApp(this)
    }
}
