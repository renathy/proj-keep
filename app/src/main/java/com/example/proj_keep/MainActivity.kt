package com.example.proj_keep

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import lv.romstr.mobile.rtu_android.Database
import lv.romstr.mobile.rtu_android.Note

class MainActivity : AppCompatActivity() {
    private val db get() = Database.getInstance(this)
    private val notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d("Started", notes.toString())
        getNotes()

        btnAddImageNote.setOnClickListener{
            openCreateImageNote()
        }

        btnAddSimpleNote.setOnClickListener{
            openCreateSimpleNote()
        }

    }

    private fun openCreateImageNote() {
        val intent = Intent(this, CreateNoteActivity::class.java)

        startActivity(intent)
    }

    private fun openCreateSimpleNote() {
        val intent = Intent(this, CreateNoteActivity::class.java).apply {
            putExtra(SEND_MESSAGE_EXTRA, "dummymessage")
        }
        startActivityForResult(intent, REPLY_REQUEST_CODE)
    }

    private fun getNotes() {
        notes.addAll(db.noteDao().getAll())
        Log.d("NOTES", notes.toString())
    }

    private fun reloadNotes() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REPLY_REQUEST_CODE) {
            data?.let {
                reloadNotes()
            }
        } else {
            // do nothing
        }


    }

    companion object {
        const val REPLY_REQUEST_CODE = 1234
        const val SEND_MESSAGE_EXTRA = "lv.romstr.mobile.rtu_android.send_message"
    }
}