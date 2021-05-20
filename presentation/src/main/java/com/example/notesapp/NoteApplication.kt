package com.example.notesapp

import android.app.Application
import com.example.data.di.databaseModule
import com.example.data.di.repositoryModule
import com.example.notesapp.di.useCasesModule
import com.example.notesapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class NoteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NoteApplication)
            modules(listOf(
                databaseModule,
                repositoryModule,
                viewModelsModule,
                useCasesModule
            ))
        }
    }
}