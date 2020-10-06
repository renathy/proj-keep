package lv.romstr.mobile.rtu_android

import androidx.room.*

@Entity(tableName = "note")
data class Note(
    val note: String,
    val title: String,
    val createDate: String,
    val imagePath: String,
    val color: String,

    @PrimaryKey(autoGenerate = true) var id: Long = 0
)