package com.example.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entity.Note


@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "picture") val picture: String?

) {
    fun toNote(): Note {
        return Note(id = id, note = note, picture = picture)
    }

    companion object {
        fun fromNote(note: Note) : NoteEntity {
            note.id?.let {
                return NoteEntity(id = it, note = note.note, picture = note.picture)
            } ?: kotlin.run {
                return NoteEntity(note = note.note, picture = note.picture)
            }

        }
    }
}