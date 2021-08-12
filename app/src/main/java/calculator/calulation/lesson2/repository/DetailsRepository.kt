package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.WeatherDTO


interface DetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double, callback: retrofit2.Callback<WeatherDTO>
    )
}