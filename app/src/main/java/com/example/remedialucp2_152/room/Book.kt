package com.example.remedialucp2_152.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,
    val title: String,
    val isbn: String,
    val publicationYear: Int,
    val categoryId: Int
)
