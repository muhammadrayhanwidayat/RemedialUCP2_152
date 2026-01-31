package com.example.remedialucp2_152.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Simplified for brevity, omitting Copy/CrossRef for now unless needed by build
// Ideally safely include all previous entities
@Database(
    entities = [
        Book::class, 
        Category::class,
        Author::class
        // Add others if recreated
    ], 
    version = 2,
    exportSchema = false
)
abstract class LibraryDatabase : RoomDatabase() {

    abstract fun libraryDao(): LibraryDao

    companion object {
        @Volatile
        private var INSTANCE: LibraryDatabase? = null

        fun getDatabase(context: Context): LibraryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LibraryDatabase::class.java,
                    "library_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
