package com.example.domain.usecase

import com.example.domain.repository.NotesRepository

class DeleteNoteUseCase(private val notesRepository: NotesRepository) {
    suspend fun deleteNote(noteId: Int) {
        return notesRepository.deleteNote(noteId = noteId)
    }
}