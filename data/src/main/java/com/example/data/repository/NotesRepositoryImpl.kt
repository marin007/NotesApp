package com.example.data.repository

import com.example.data.cache.AppDataBase
import com.example.data.entity.NoteEntity
import com.example.domain.entity.Note
import com.example.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NotesRepositoryImpl(
    private val database: AppDataBase
) : NotesRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return database.noteDao().getAllNotes().map { it.map { item -> item.toNote() } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun insertNote(note: Note) = withContext(Dispatchers.IO) {
        database.noteDao().insertNote(
            NoteEntity.fromNote(note)
        )
    }

    override suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        database.noteDao().updateNote(
            NoteEntity.fromNote(note)
        )
    }

    override suspend fun deleteNote(noteId: Int) = withContext(Dispatchers.IO) {
        database.noteDao().deleteNote(
            noteId
        )
    }
}