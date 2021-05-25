package com.example.learnroomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.learnroomdb.db.note.Note
import com.example.learnroomdb.db.note.NoteDao

@Database(entities = [Note::class],exportSchema = false,version = 1)
abstract class Database:RoomDatabase() {
    abstract fun noteDao():NoteDao

    companion object{
        private const val NamaDB = "NOTE_DB"
        private var instance: com.example.learnroomdb.db.Database?=null

        fun getInstance(context: Context):com.example.learnroomdb.db.Database?{
            if (instance == null){
                synchronized(com.example.learnroomdb.db.Database::class){
                    instance = Room
                        .databaseBuilder(
                            context,
                            com.example.learnroomdb.db.Database::class.java,
                            NamaDB
                        )
                        .build()
                }
            }
            return instance
        }
    }
}