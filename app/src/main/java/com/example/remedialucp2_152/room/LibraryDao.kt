package com.example.remedialucp2_152.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LibraryDao {


    @Insert
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books WHERE bookId = :id")
    suspend fun getBookById(id: Int): Book?

    @Delete
    suspend fun deleteBook(book: Book)


    @Insert
    suspend fun insertAuthor(author: Author)

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): LiveData<List<Author>>


    @Insert
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE categoryId = :id")
    suspend fun getCategoryById(id: Int): Category?
    

    @Transaction
    @Query("""
        WITH RECURSIVE CategoryHierarchy(categoryId) AS (
            SELECT categoryId FROM categories WHERE categoryId = :parentId
            UNION ALL
            SELECT c.categoryId FROM categories c
            INNER JOIN CategoryHierarchy ch ON c.parentId = ch.categoryId
        )
        SELECT * FROM books WHERE categoryId IN (SELECT categoryId FROM CategoryHierarchy)
    """)
    fun getBooksByCategoryRecursive(parentId: Int): LiveData<List<Book>>
    

    @Query("""
        WITH RECURSIVE CategoryHierarchy(categoryId) AS (
            SELECT categoryId FROM categories WHERE parentId = :targetId
            UNION ALL
            SELECT c.categoryId FROM categories c
            INNER JOIN CategoryHierarchy ch ON c.parentId = ch.categoryId
        )
        SELECT categoryId FROM CategoryHierarchy
    """)
    suspend fun getDescendants(targetId: Int): List<Int>
}
