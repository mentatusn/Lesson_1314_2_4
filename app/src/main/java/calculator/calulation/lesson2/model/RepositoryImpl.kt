package calculator.calulation.lesson2.model

import calculator.calulation.lesson2.view.Weather
import calculator.calulation.lesson2.view.getRussianCities
import calculator.calulation.lesson2.view.getWorldCities

class RepositoryImpl:Repository {
    override fun getWeatherFromServer()= Weather()
    override fun getWeatherFromLocalRussian() =  getRussianCities()
    override fun getWeatherFromLocalWorld() =  getWorldCities()
}