package com.example.proj_keep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val intent = Intent(this, CreateNoteActivity::class.java)

        startActivity(intent)
    }
}