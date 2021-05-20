package com.example.domain.repository

import com.example.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(noteId: Int)
}