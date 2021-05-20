package com.example.notesapp.ui.notes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.domain.entity.Note
import com.example.domain.usecase.*
import com.example.notesapp.R
import com.example.notesapp.common.ValueWrapper
import com.example.notesapp.ui.notes.noteslist.NotesListAdapter
import kotlinx.coroutines.launch
import java.io.File

class NotesViewModel(
    getAllNotesUseCase: GetAllNotesUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val deleteFileUseCase: DeleteFileUseCase,
    private val createFileUseCase: CreateFileUseCase
) : ViewModel() {

    var noteId: Int = 0

    val notes: LiveData<List<Note>> = getAllNotesUseCase.getAllNotes().asLiveData()

    val noteText = ObservableField<String>()

    val picturePath = ObservableField<String>()

    lateinit var listAdapter: NotesListAdapter

    private val _navigation = MutableLiveData<ValueWrapper<Int>>()
    val navigation: LiveData<ValueWrapper<Int>>
        get() = _navigation

    fun insertNote(noteText: String, picture: String? = null) {
        viewModelScope.launch {
            val note: Note =
                if (noteId > 0) Note(id = noteId, note = noteText, picture = picture) else {
                    Note(note = noteText, picture = picture)
                }
            insertNoteUseCase.insertNote(note)
            moveToNotesScreen()
        }
    }

    fun updateNote(noteText: String, picture: String? = null) {
        viewModelScope.launch {
            val note: Note =
                if (noteId > 0) Note(id = noteId, note = noteText, picture = picture) else {
                    Note(note = noteText, picture = picture)
                }
            updateNoteUseCase.updateNote(note)
            moveToNotesScreen()
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            deleteNoteUseCase.deleteNote(noteId = noteId)
        }
    }

    fun moveToEditNotesScreen() {
        val resId = R.id.action_notesFragment_to_editNoteFragment
        _navigation.value = ValueWrapper(resId)
    }

    fun clearNoteData() {
        noteId = 0
        picturePath.set(null)
        noteText.set(null)
    }

    fun getBitmapPicture(filePath: String): Bitmap {
        return BitmapFactory.decodeFile(filePath)
    }

    private fun moveToNotesScreen() {
        val resId = R.id.action_editNoteFragment_to_notesFragment
        _navigation.value = ValueWrapper(resId)
    }

    suspend fun deletePictureFile(path: String): Boolean {
        return deleteFileUseCase.deleteFile(path)
    }

    suspend fun createFile(fileDir: File?): File {
        return createFileUseCase.createFile(fileDir = fileDir)
    }

}