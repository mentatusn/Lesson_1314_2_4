package calculator.calulation.lesson2.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryEntity::class
    ,HistoryEntity2::class),version = 1)
abstract class HistoryDataBase: RoomDatabase() {
    abstract fun historyDao():HistoryDao
}