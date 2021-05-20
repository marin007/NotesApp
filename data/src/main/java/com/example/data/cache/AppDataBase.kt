package com.example.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}