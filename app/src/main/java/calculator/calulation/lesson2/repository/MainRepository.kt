package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.Weather

interface MainRepository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalRussian(): List<Weather>
    fun getWeatherFromLocalWorld(): List<Weather>
}