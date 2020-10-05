package com.example.proj_keep

import androidx.room.*
import lv.romstr.mobile.rtu_android.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY id DESC")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id = :itemId")
    fun getItemById(itemId: Long): Note

    @Insert
    fun insert(vararg item: Note): List<Long>

    @Update
    fun update(item: Note)

    @Delete
    fun delete(item: Note)
}