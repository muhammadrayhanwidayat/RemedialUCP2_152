package com.example.remedialucp2_152.repository

import android.content.Context
import com.example.remedialucp2_152.room.LibraryDatabase

interface AppContainer {
    val libraryRepository: LibraryRepository
}

class ContainerApp(private val context: Context) : AppContainer {
    override val libraryRepository: LibraryRepository by lazy {
        val database = LibraryDatabase.getDatabase(context)
        LibraryRepository(database.libraryDao())
    }
}
