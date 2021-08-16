package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.Weather
import calculator.calulation.lesson2.room.HistoryDao
import calculator.calulation.utils.convertModelToEntity
import calculator.calulation.utils.convertToEntityModel

class LocalRepositoryImpl(private val historyDao: HistoryDao) : LocalRepository {
    override fun getAllHistory(): List<Weather> {
        return convertToEntityModel(historyDao.selectAll())
    }

    override fun saveEntity(weather: Weather) {
        historyDao.insert(convertModelToEntity(weather))
    }
}


