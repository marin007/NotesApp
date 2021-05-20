package com.example.domain.repository

import java.io.File

interface FileOperationsRepository {

    suspend fun createFile(fileDir: File?): File

    suspend fun deleteFile(filePath: String): Boolean
}