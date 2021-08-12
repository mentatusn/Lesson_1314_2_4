package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.Weather
import calculator.calulation.lesson2.model.getRussianCities
import calculator.calulation.lesson2.model.getWorldCities

class MainRepositoryImpl : MainRepository {
    override fun getWeatherFromServer() = Weather()
    override fun getWeatherFromLocalRussian() = getRussianCities()
    override fun getWeatherFromLocalWorld() = getWorldCities()
}