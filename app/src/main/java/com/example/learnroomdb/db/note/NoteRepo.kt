package com.example.learnroomdb.db.note

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.learnroomdb.db.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NoteRepo(application: Application) {
    private val noteDao: NoteDao?
    private var notes: LiveData<List<Note>>? = null

    init {
        val db= Database.getInstance(application.applicationContext)
        noteDao= db?.noteDao()
        notes =noteDao?.getNote()
    }

    fun getNotes():LiveData<List<Note>>?{
        return notes
    }

    fun insert(note: Note)= runBlocking {
        this.launch(Dispatchers.IO){
            noteDao?.insertNote(note)
        }
    }

    fun delete(note: Note) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                noteDao?.deleteNote(note)
            }
        }
    }

    fun update(note: Note) = runBlocking {
        this.launch(Dispatchers.IO) {
            noteDao?.updateNote(note)
        }
    }
}