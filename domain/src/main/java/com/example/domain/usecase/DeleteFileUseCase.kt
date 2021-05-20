package com.example.domain.usecase

import com.example.domain.repository.FileOperationsRepository

class DeleteFileUseCase(private val fileOperationsRepository: FileOperationsRepository) {
    suspend fun deleteFile(filePath: String): Boolean {
        return fileOperationsRepository.deleteFile(filePath = filePath)
    }
}