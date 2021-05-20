package com.example.data.di

import com.example.data.cache.AppDataBase
import com.example.data.repository.FileOperationsRepositoryImpl
import com.example.data.repository.NotesRepositoryImpl
import com.example.domain.repository.FileOperationsRepository
import com.example.domain.repository.NotesRepository
import org.koin.dsl.module


val repositoryModule = module {
    single { createNoteRepository(get()) }
    single { createFileRepository() }
}

fun createNoteRepository(database: AppDataBase): NotesRepository {
    return NotesRepositoryImpl(
        database
    )
}

fun createFileRepository(): FileOperationsRepository {
    return FileOperationsRepositoryImpl()
}
