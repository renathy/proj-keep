package lv.romstr.mobile.rtu_android

import androidx.room.*

@Entity(tableName = "note")
data class Note(
    val note: String,
    val title: String,
    // todo: datetime
    val imagePath: String,

    @PrimaryKey(autoGenerate = true) var id: Long = 0
)