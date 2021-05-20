package com.example.domain.usecase

import com.example.domain.repository.FileOperationsRepository
import java.io.File

class CreateFileUseCase(private val fileOperationsRepository: FileOperationsRepository) {
    suspend fun createFile(fileDir: File?): File {
        return fileOperationsRepository.createFile(fileDir = fileDir)
    }
}