package calculator.calulation.lesson2.room

import androidx.room.Entity
import androidx.room.PrimaryKey



const val ID ="id"
const val NAME ="name"
const val TEMPERATURE ="temperature"
@Entity
data class HistoryEntity(@PrimaryKey(autoGenerate = true) var id:Long=0,
                         var name: String="",
                         var temperature: Int=0,
                         var condition: String=""
                         )