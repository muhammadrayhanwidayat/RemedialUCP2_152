package com.example.remedialucp2_152.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.remedialucp2_152.repository.LibraryRepository
import com.example.remedialucp2_152.room.Book
import com.example.remedialucp2_152.room.Category

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: LibraryRepository) : ViewModel() {


    val allBooks: LiveData<List<Book>> = repository.allBooks
    val allCategories: LiveData<List<Category>> = repository.allCategories
    
    fun getBooksByCategory(categoryId: Int): LiveData<List<Book>> {
        return repository.getBooksByCategoryRecursive(categoryId)
    }


    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
}
