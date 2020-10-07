package com.example.proj_keep

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.proj_keep.MainActivity.Companion.EXTRA_ID
import com.example.proj_keep.MainActivity.Companion.REQUEST_CREATE
import kotlinx.android.synthetic.main.activity_create_note.*
import lv.romstr.mobile.rtu_android.Database
import lv.romstr.mobile.rtu_android.Note
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {
    private val db get() = Database.getInstance(this)
    private var selectedNoteColor :String = ""
    private var  existingNote: Note? = null
    private var existingId: Long = -1;

    private var color3Str: String = ""
    private var color2Str:String = ""
    private var color1Str:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        color1Str = String.format("#%06X", (0xFFFFFF and ContextCompat.getColor(this, R.color.colorNoteColor)))
        color2Str = String.format("#%06X", (0xFFFFFF and ContextCompat.getColor(this, R.color.colorNoteColor2)))
        color3Str = String.format("#%06X", (0xFFFFFF and ContextCompat.getColor(this, R.color.colorNoteColor3)))

        if (isExistingNoteEditing()) {
            loadNoteData()

            btnAddOrUpdateNote.setImageResource(R.drawable.ic_done)

        } else {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate: String = sdf.format(Date())
            textNoteDate.text = currentDate

            selectedNoteColor = color2Str //default color


            btnAddOrUpdateNote.setImageResource(R.drawable.ic_add)

        }
        setColorListenersAndColor()

        btnAddOrUpdateNote.setOnClickListener {
            createOrUpdateNote()
        }

         btnReturnToMain.setOnClickListener {
             openMainScreenGoBack()
        }
    }

    private fun setColorListenersAndColor() {
        viewColor2.setOnClickListener {

            this.selectedNoteColor = color2Str
            imgColor2.setImageResource(R.drawable.ic_done)
            imgColor1.setImageResource(0)
            imgColor3.setImageResource(0)
            setColor()
        }
        viewColor1.setOnClickListener {
           this.selectedNoteColor = color1Str
            imgColor1.setImageResource(R.drawable.ic_done)
            imgColor2.setImageResource(0)
            imgColor3.setImageResource(0)
            setColor()
        }
        viewColor3.setOnClickListener {

            this.selectedNoteColor = color3Str
            imgColor3.setImageResource(R.drawable.ic_done)
            imgColor1.setImageResource(0)
            imgColor2.setImageResource(0)
            setColor()
        }


        if (this.selectedNoteColor == color3Str) {
            viewColor3.performClick()
        } else if (this.selectedNoteColor == color1Str) {
            viewColor1.performClick()
        } else {
            viewColor2.performClick()
        }
    }

    private fun loadNoteData() {

        val item = db.noteDao().getItemById(existingId)
        existingNote = item

        editTextTitle.setText(item.title)
        editTextNote.setText(item.note)
        textNoteDate.text = item.createDate
        selectedNoteColor = item.color

    }

    private fun isExistingNoteEditing(): Boolean {
        existingId = intent.getLongExtra(EXTRA_ID, 0)
        return (existingId > 0)
    }

    private fun openMainScreenGoBack() {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "value")
        }
        startActivity(intent)
    }


    private fun openMainScreenAddingNote() {
        val intent = Intent().putExtra(EXTRA_ID, "created")

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun openMainScreenUpdatingNote() {
        val intent = Intent().putExtra(EXTRA_ID, "created")

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun createOrUpdateNote() {
        var title = editTextTitle.text.toString()
        var noteText = editTextNote.text.toString()

        if (title.trim().isEmpty() || noteText.trim().isEmpty()) {
            Toast.makeText(this, "Note title or text is empty", Toast.LENGTH_SHORT).show();
            return;
        }


        var createDate = textNoteDate.text.toString()
        var imagePath = ""

        if (existingNote != null) {
            existingNote!!.title = title
            existingNote!!.note = noteText
            existingNote!!.createDate = createDate
            existingNote!!.imagePath = imagePath
            existingNote!!.color = selectedNoteColor

            db.noteDao().update(existingNote!!)

            openMainScreenUpdatingNote()
        } else {
            var item = Note(noteText, title, createDate, imagePath, selectedNoteColor)
            item.id = db.noteDao().insert(item).first()

            openMainScreenAddingNote()
        }
    }


    private fun setColor() {
        viewTitleColorHorizLine.setBackgroundColor(Color.parseColor(this.selectedNoteColor));
    }
}