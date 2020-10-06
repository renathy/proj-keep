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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS

class MainActivity : AppCompatActivity() {
    private val db get() = Database.getInstance(this)
    private val notes = mutableListOf<Note>()

    private lateinit var adapter: NotesRecyclerAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d("Started", notes.toString())
        notes.addAll(db.noteDao().getAll())
        Log.d("NOTES", notes.toString())

        layoutManager =
            StaggeredGridLayoutManager(
                resources.getInteger(R.integer.span_count), StaggeredGridLayoutManager.VERTICAL
            ).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }

        recyclerItems.layoutManager = layoutManager

        adapter = NotesRecyclerAdapter(notes)
        recyclerItems.adapter = adapter
        recyclerItems.smoothScrollToPosition(0)

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

    private fun reloadNotes() {
        notes.add(0, db.noteDao().getLast())
        Log.d("NOTES", notes.toString())
        adapter.notifyItemInserted(0)

        recyclerItems.smoothScrollToPosition(0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REPLY_REQUEST_CODE) {
            data?.let {
                Log.d("reload", "reload")
                reloadNotes()
            }
        } else {
            // do nothing
            Log.d("reload", "just return")
        }
    }

    companion object {
        const val REPLY_REQUEST_CODE = 1234
        const val SEND_MESSAGE_EXTRA = "lv.romstr.mobile.rtu_android.send_message"
    }
}