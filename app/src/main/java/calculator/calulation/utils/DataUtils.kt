package calculator.calulation.utils

import calculator.calulation.lesson2.model.*
import calculator.calulation.lesson2.room.HistoryEntity

fun convertWeatherDtoToModel (weatherDTO: WeatherDTO):List<Weather>{ //FIXME
    val factDTO: FactDTO =weatherDTO.fact
    return listOf(Weather(getDefaultCity(),factDTO.temp,factDTO.feels_like,factDTO.condition,factDTO.icon)) //FIXME
}

fun convertToEntityModel(historyEntity: List<HistoryEntity>): List<Weather> {
    return historyEntity.map {
        Weather(City(it.name, 0.0, 0.0), it.temperature, 0, it.condition) //TODO временный костыль
    }
}

fun convertModelToEntity(weather: Weather): HistoryEntity {
    return HistoryEntity(0, weather.city.name, weather.temperature, weather.condition) //TODO id нет
}