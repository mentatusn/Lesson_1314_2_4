package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.WeatherDTO
import calculator.calulation.lesson2.model.YANDEX_API_KEY_VALUE
import calculator.calulation.lesson2.model.YANDEX_API_URL
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val weatherAPI = Retrofit.Builder()
        .baseUrl(YANDEX_API_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())) // TODO setLenient()
        .build().create(WeatherAPI::class.java)

    fun getWeatherDetails(lat:Double,lon:Double, callback: Callback<WeatherDTO>) {
        weatherAPI.getWeather(YANDEX_API_KEY_VALUE,lat,lon).enqueue(callback)
    }
}