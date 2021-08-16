package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.Weather

interface LocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather:Weather)
    fun deleteEntityByName(name:String)
}