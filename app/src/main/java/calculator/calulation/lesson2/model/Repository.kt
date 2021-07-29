package calculator.calulation.lesson2.model

import calculator.calulation.lesson2.view.Weather

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalRussian(): List<Weather>
    fun getWeatherFromLocalWorld(): List<Weather>
}