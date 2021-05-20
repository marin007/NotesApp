package com.example.domain.usecase

import com.example.domain.entity.Note
import com.example.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase (private val notesRepository: NotesRepository) {
    fun getAllNotes() : Flow<List<Note>> {
        return notesRepository.getAllNotes()
    }
}