package com.example.notesapp.di

import com.example.domain.usecase.*
import com.example.notesapp.ui.notes.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelsModule = module {
    viewModel {
        NotesViewModel(get(), get(), get(), get(), get(), get())
    }
}
val useCasesModule = module {
    factory {
        GetAllNotesUseCase(
            notesRepository = get()
        )
    }
    factory {
        InsertNoteUseCase(
            notesRepository = get()
        )
    }
    factory {
        UpdateNoteUseCase(
            notesRepository = get()
        )
    }
    factory {
        DeleteNoteUseCase(
            notesRepository = get()
        )
    }
    factory {
        CreateFileUseCase(
            fileOperationsRepository = get()
        )
    }
    factory {
        DeleteFileUseCase(
            fileOperationsRepository = get()
        )
    }
}