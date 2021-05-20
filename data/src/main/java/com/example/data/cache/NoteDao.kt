package com.example.data.cache

import androidx.room.*
import com.example.data.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM noteentity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(vararg note: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(vararg note: NoteEntity)

    @Query("DELETE FROM noteentity WHERE id = :noteId")
    fun deleteNote(noteId: Int)
}