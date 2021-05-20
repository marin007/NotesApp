package com.example.domain.usecase

import com.example.domain.entity.Note
import com.example.domain.repository.NotesRepository

class UpdateNoteUseCase(private val notesRepository: NotesRepository) {
    suspend fun updateNote(note: Note) {
        return notesRepository.updateNote(note = note)
    }
}