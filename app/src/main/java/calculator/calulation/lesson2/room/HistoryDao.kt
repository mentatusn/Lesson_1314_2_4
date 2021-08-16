package calculator.calulation.lesson2.room

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    fun selectAll(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE :cityName")
    fun selectByWord(cityName: String): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity WHERE name=:cityName")
    fun deleteByWordTestDelete(cityName: String)

    @Query("UPDATE HistoryEntity SET temperature=:temp WHERE name=:cityName")
    fun deleteByWordTestUpdate(temp: Int,cityName: String)

    @Query("INSERT INTO HistoryEntity (temperature,name,condition) VALUES(:temp,:cityName,:condition)")
    fun deleteByWordTestInsert(temp: Int,cityName: String,condition:String)

    @Insert(onConflict = IGNORE)
    fun insert(historyEntity:HistoryEntity)

    @Update
    fun update(historyEntity:HistoryEntity)

    @Delete
    fun delete(historyEntity:HistoryEntity)
}