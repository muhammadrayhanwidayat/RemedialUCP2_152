package com.example.remedialucp2_152.repository

import androidx.lifecycle.LiveData
import com.example.remedialucp2_152.room.LibraryDao
import com.example.remedialucp2_152.room.Book
import com.example.remedialucp2_152.room.Category
import com.example.remedialucp2_152.room.Author

class LibraryRepository(private val libraryDao: LibraryDao) {

    // --- Books ---
    val allBooks: LiveData<List<Book>> = libraryDao.getAllBooks()

    suspend fun insertBook(book: Book) {
        libraryDao.insertBook(book)
    }

    suspend fun deleteBook(book: Book) {
        libraryDao.deleteBook(book)
    }
    
    fun getBooksByCategoryRecursive(parentId: Int): LiveData<List<Book>> {
        return libraryDao.getBooksByCategoryRecursive(parentId)
    }

    // --- Authors ---
    suspend fun insertAuthor(author: Author) {
        libraryDao.insertAuthor(author)
    }

    // --- Categories ---
    val allCategories: LiveData<List<Category>> = libraryDao.getAllCategories()

    suspend fun insertCategory(category: Category) {
        libraryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        libraryDao.updateCategory(category)
    }

    suspend fun getCategoryById(id: Int): Category? {
        return libraryDao.getCategoryById(id)
    }
    
    // Check if newParentId is a descendant of categoryId (to prevent cycles)
    suspend fun isCyclic(categoryId: Int, newParentId: Int): Boolean {
        if (categoryId == newParentId) return true // Cannot be own parent
        
        val descendants = libraryDao.getDescendants(categoryId)
        // If the new parent is found in the descendants list, it's a cycle
        return descendants.contains(newParentId)
    }
}
