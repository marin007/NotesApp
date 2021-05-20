package com.example.data.repository

import android.annotation.SuppressLint
import com.example.domain.repository.FileOperationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FileOperationsRepositoryImpl: FileOperationsRepository {

    @SuppressLint("SimpleDateFormat")
    override suspend fun createFile(fileDir: File?): File = withContext(Dispatchers.IO) {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            fileDir
        )
    }

    override suspend fun deleteFile(filePath: String): Boolean = withContext(Dispatchers.IO) {
        val file = File(filePath)
        file.delete()
    }
}