package com.example.proj_keep

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import lv.romstr.mobile.rtu_android.Database
import lv.romstr.mobile.rtu_android.Note
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity(), AdapterClickListener {
    private val db get() = Database.getInstance(this)
    private val notes = mutableListOf<Note>()

    private lateinit var adapter: NotesRecyclerAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAdapterAndLayoutManager()

        btnAddImageNote.setOnClickListener{
            openCreateImageNote()
        }

        btnAddSimpleNote.setOnClickListener{
            openCreateSimpleNote()
        }
    }

    private fun setUpAdapterAndLayoutManager() {
        notes.addAll(db.noteDao().getAll())
        Log.d("NOTES", notes.toString())

        layoutManager =
            StaggeredGridLayoutManager(
                resources.getInteger(R.integer.span_count), StaggeredGridLayoutManager.VERTICAL
            ).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }

        recyclerItems.layoutManager = layoutManager

        adapter = NotesRecyclerAdapter(this, notes)
        recyclerItems.adapter = adapter
        recyclerItems.smoothScrollToPosition(0)
    }

    private fun openCreateImageNote() {
        val intent = Intent(this, CreateNoteActivity::class.java)

        startActivity(intent)
    }

    private fun openCreateSimpleNote() {
        val intent = Intent(this, CreateNoteActivity::class.java)
        startActivityForResult(intent, REQUEST_CREATE)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CREATE) {
            data?.let {
                Log.d("reload", "reload")
                noteWasInserted()
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CREATE) {
            noteWasUpdated()
        } else {
            // do nothing
            Log.d("reload", "just return")
        }
    }

    private fun noteWasInserted() {
        notes.add(0, db.noteDao().getLast())
        Log.d("NOTES", notes.toString())
        adapter.notifyItemInserted(0)

        recyclerItems.smoothScrollToPosition(0)
    }

    private fun noteWasUpdated() {
        TODO("Change position")
        adapter.notifyItemChanged(0)
    }

    override fun deleteClicked(item: Note) {
        db.noteDao().delete(item)
    }

    override fun itemClicked(item: Note) {
        val intent = Intent(this, CreateNoteActivity::class.java)
            .putExtra(EXTRA_ID, item.id)

        startActivityForResult(intent, REQUEST_UPDATE)
    }

    companion object {
        const val REQUEST_CREATE = 1
        const val REQUEST_UPDATE = 2
        const val EXTRA_ID = "lv.romstr.mobile.extras.note_id"
        const val RESULT_CREATE_OR_UPDATE = "lv.romstr.mobile.extras.result"
    }
}