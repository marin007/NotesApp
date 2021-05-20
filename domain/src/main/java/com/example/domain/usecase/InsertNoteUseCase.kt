package com.example.domain.usecase

import com.example.domain.entity.Note
import com.example.domain.repository.NotesRepository

class InsertNoteUseCase(private val notesRepository: NotesRepository) {
    suspend fun insertNote(note: Note) {
        return notesRepository.insertNote(note = note)
    }
}