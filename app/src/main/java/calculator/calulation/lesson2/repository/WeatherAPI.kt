package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.WeatherDTO
import calculator.calulation.lesson2.model.YANDEX_API_KEY_NAME
import calculator.calulation.lesson2.model.YANDEX_API_URL_END_POINT
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {
    @GET(YANDEX_API_URL_END_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY_NAME) token:String,
        @Query("lat") lat:Double,
        @Query("lon") lon:Double
    ): retrofit2.Call<WeatherDTO>
}