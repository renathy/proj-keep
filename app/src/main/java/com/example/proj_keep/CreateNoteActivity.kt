package com.example.proj_keep

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_create_note.*

class CreateNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        btnAddCreatedNote.setOnClickListener {
            openMainScreenAddingNote()
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
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "value")
        }
        startActivity(intent)
    }
}