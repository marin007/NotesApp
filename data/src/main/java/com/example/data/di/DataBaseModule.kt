package com.example.data.di

import androidx.room.Room
import com.example.data.cache.AppDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { Room.databaseBuilder(androidApplication(), AppDataBase::class.java, "database-notes-app").build() }
    single { get<AppDataBase>().noteDao() }
}