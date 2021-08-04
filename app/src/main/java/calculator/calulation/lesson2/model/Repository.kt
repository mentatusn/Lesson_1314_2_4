package calculator.calulation.lesson2.model

interface Repository {
    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalRussian(): List<Weather>
    fun getWeatherFromLocalWorld(): List<Weather>
}