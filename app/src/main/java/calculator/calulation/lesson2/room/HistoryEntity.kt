package calculator.calulation.lesson2.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(@PrimaryKey(autoGenerate = true) val id:Int,
                         val name: String,
                         val temperature: Int,
                         val condition: String,
                         val condition2: String=""
                         )
@Entity
data class HistoryEntity2(@PrimaryKey(autoGenerate = true) val id:Int,
                         val name: String,
                         val temperature: Int,
                         val condition: String)