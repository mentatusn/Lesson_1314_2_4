package calculator.calulation.lesson2.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(HistoryEntity::class
    ,HistoryEntity2::class),version = 2)
abstract class HistoryDataBase: RoomDatabase() {
    abstract fun historyDao():HistoryDao
}

val MIGRATION_1_2:Migration = object :Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
       database.execSQL("ALTER TABLE HistoryEntity ADD COLUMN condition2 TEXT NOT NULL DEFAULT ''")
    }
}