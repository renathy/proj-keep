package com.example.proj_keep

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proj_keep.MainActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.activity_create_note.*
import lv.romstr.mobile.rtu_android.Database
import lv.romstr.mobile.rtu_android.Note
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {
    private val db get() = Database.getInstance(this)
    private var selectedNoteColor :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)



        if (IsExistingNoteEditing()) {
            loadNoteData()
        } else {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate: String = sdf.format(Date())
            textNoteDate.text = currentDate

            selectedNoteColor = "#fdbe38" //default color

        }
        setColorListenersAndColor()
        //val message = intent.getStringExtra(SEND_MESSAGE_EXTRA)

        btnAddCreatedNote.setOnClickListener {
            saveSimpleNote()
        }

         btnReturnToMain.setOnClickListener {
             openMainScreenGoBack()
        }


    }

    private fun setColorListenersAndColor() {
        viewColor2.setOnClickListener {
            this.selectedNoteColor = "#fdbe38"
            imgColor2.setImageResource(R.drawable.ic_done)
            imgColor1.setImageResource(0)
            imgColor3.setImageResource(0)
            setColor()
        }
        viewColor1.setOnClickListener {
            this.selectedNoteColor = "#333333"
            imgColor1.setImageResource(R.drawable.ic_done)
            imgColor2.setImageResource(0)
            imgColor3.setImageResource(0)
            setColor()
        }
        viewColor3.setOnClickListener {
            this.selectedNoteColor = "#aabbcc"
            imgColor3.setImageResource(R.drawable.ic_done)
            imgColor1.setImageResource(0)
            imgColor2.setImageResource(0)
            setColor()
        }


        if (this.selectedNoteColor == "#aabbcc") {
            viewColor3.performClick()
        } else if (this.selectedNoteColor == "#333333") {
            viewColor1.performClick()
        } else {
            viewColor2.performClick()
        }
    }

    private fun loadNoteData() {
        val id = intent.getLongExtra(EXTRA_ID, 0)
        val item = db.noteDao().getItemById(id)

        editTextTitle.setText(item.title)
        editTextNote.setText(item.note)
        textNoteDate.text = item.createDate
        selectedNoteColor = item.color

    }

    private fun IsExistingNoteEditing(): Boolean {
        return false;
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
        var title = editTextTitle.text.toString()
        var noteText = editTextNote.text.toString()
        var createDate = textNoteDate.text.toString()
        var imagePath = ""

        if (title.trim().isEmpty() || noteText.trim().isEmpty()) {
            Toast.makeText(this, "Note title or text is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        var item = Note(noteText, title, createDate, imagePath, selectedNoteColor)
        item.id = db.noteDao().insert(item).first()

        openMainScreenAddingNote()
    }

    private fun setColor() {
        viewTitleColorHorizLine.setBackgroundColor(Color.parseColor(this.selectedNoteColor));
    }

    companion object {
        const val REPLY_MESSAGE_EXTRA = "lv.romstr.mobile.rtu_android.reply_message"
    }
}