package com.example.proj_keep

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_note.*
import lv.romstr.mobile.rtu_android.Database
import lv.romstr.mobile.rtu_android.Note

class CreateNoteActivity : AppCompatActivity() {
    private val db get() = Database.getInstance(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        // we can get dummytext clear, but no need fornow
        //val message = intent.getStringExtra(SEND_MESSAGE_EXTRA)

        btnAddCreatedNote.setOnClickListener {
            saveSimpleNote()
        }

         btnReturnToMain.setOnClickListener {
             openMainScreenGoBack()
        }
    }



    private fun openMainScreenGoBack() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "value")
        }
        startActivity(intent)
    }


    private fun openMainScreenAddingNote() {
        val intent = Intent().apply {
            putExtra(REPLY_MESSAGE_EXTRA, "note added info")
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun saveSimpleNote() {
        // todo: check if empty
        /*
            Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show();
            return;
        }*/
        var title = editTextTitle.text.toString()
        var noteText = editTextNote.text.toString()

        var item = Note(title, noteText, "")
        item.id = db.noteDao().insert(item).first()

        openMainScreenAddingNote()
    }

    companion object {
        const val REPLY_MESSAGE_EXTRA = "lv.romstr.mobile.rtu_android.reply_message"
    }
}