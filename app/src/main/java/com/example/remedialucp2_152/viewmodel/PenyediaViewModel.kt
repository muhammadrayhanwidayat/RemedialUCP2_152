package com.example.remedialucp2_152.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.remedialucp2_152.LibraryApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(libraryApp().container.libraryRepository)
        }
        initializer {
            EntryViewModel(libraryApp().container.libraryRepository)
        }
    }
}

fun CreationExtras.libraryApp(): LibraryApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibraryApp)
