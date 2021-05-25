package com.example.learnroomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.learnroomdb.db.note.Note


class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: ViewModel
    private lateinit var noteAdapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        noteUtama.layoutManager = LinearLayoutManager(this)
        noteAdapter = Adapter(this){note,i->
            showAlertMenu(note)
        }
        noteUtama.adapter=noteAdapter
        noteViewModel = ViewModelProvider(this).get(ViewModel::class.java)
        noteViewModel.getNotes()?.observe(this, Observer {
            noteAdapter.setNotes(it)
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addMenu -> showAlertDialogAdd()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialogAdd() {
        val alert = AlertDialog.Builder(this)

        val editText = EditText(applicationContext)
        editText.hint = "Enter your text"

        alert.setTitle("New Note")
        alert.setView(editText)

        alert.setPositiveButton("Save") { dialog, _ ->
            noteViewModel.insertNotes(
                Note(note = editText.text.toString())
            )
            dialog.dismiss()
        }

        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }

    private fun showAlertMenu(note: Note) {
        val items = arrayOf("Edit", "Delete")

        val builder =
            AlertDialog.Builder(this)
        builder.setItems(items) { dialog, which ->
            when (which) {
                0 -> {
                    showAlertDialogEdit(note)
                }
                1 -> {
                    noteViewModel.deleteNote(note)
                }
            }
        }
        builder.show()
    }

    private fun showAlertDialogEdit(note: Note) {
        val alert = AlertDialog.Builder(this)

        val editText = EditText(applicationContext)
        editText.setText(note.note)

        alert.setTitle("Edit Note")
        alert.setView(editText)

        alert.setPositiveButton("Update") { dialog, _ ->
            note.note = editText.text.toString()
            noteViewModel.updateNote(note)
            dialog.dismiss()
        }

        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }
}