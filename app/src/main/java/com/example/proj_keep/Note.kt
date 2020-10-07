package lv.romstr.mobile.rtu_android

import androidx.room.*

@Entity(tableName = "note")
data class Note(
    var note: String,
    var title: String,
    var createDate: String,
    var imagePath: String,
    var color: String,

    @PrimaryKey(autoGenerate = true) var id: Long = 0
)