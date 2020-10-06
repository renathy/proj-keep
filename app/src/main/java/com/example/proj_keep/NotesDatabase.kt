package lv.romstr.mobile.rtu_android

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proj_keep.NoteDao

@Database(version = 2, entities = [Note::class])
abstract class NotesDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}

object Database {

    private var instance: NotesDatabase? = null

    fun getInstance(context: Context) = instance ?: Room.databaseBuilder(
        context.applicationContext, NotesDatabase::class.java, "notes-db"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
        .also { instance = it }
}