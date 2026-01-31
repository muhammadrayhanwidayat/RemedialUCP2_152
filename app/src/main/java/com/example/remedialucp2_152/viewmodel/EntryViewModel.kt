package com.example.remedialucp2_152.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remedialucp2_152.repository.LibraryRepository
import com.example.remedialucp2_152.room.Book
import com.example.remedialucp2_152.room.Category
import com.example.remedialucp2_152.room.Author
import kotlinx.coroutines.launch

class EntryViewModel(private val repository: LibraryRepository) : ViewModel() {


    fun insertBook(title: String, isbn: String, yearStr: String, categoryId: Int): String {

        if (title.isBlank()) {
            return "Title cannot be empty"
        }
        if (isbn.toLongOrNull() == null) {
             return "ISBN must be numeric"
        }
        val year = yearStr.toIntOrNull()
        if (year == null) {
            return "Invalid Year"
        }

        viewModelScope.launch {
            val book = Book(title = title, isbn = isbn, publicationYear = year, categoryId = categoryId)
            repository.insertBook(book)
        }
        return "Success"
    }

}
