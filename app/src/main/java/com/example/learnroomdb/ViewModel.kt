package com.example.learnroomdb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.learnroomdb.db.note.Note
import com.example.learnroomdb.db.note.NoteRepo

class ViewModel(application: Application):AndroidViewModel(application) {
    private var noteRepo = NoteRepo(application)
    private var notes:LiveData<List<Note>>?=noteRepo.getNotes()

    fun insertNotes(note: Note){
        noteRepo.insert(note)
    }

    fun getNotes(): LiveData<List<Note>>? {
        return notes
    }

    fun deleteNote(note: Note) {
        noteRepo.delete(note)
    }

    fun updateNote(note: Note) {
        noteRepo.update(note)
    }
}