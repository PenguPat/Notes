package at.fh.swengb.notes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
class Note(@PrimaryKey val id : String, var title : String, var text : String, val toUpload : Boolean)