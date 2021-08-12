package calculator.calulation.lesson2.repository

import calculator.calulation.lesson2.model.WeatherDTO
import calculator.calulation.lesson2.model.YANDEX_API_KEY_VALUE
import calculator.calulation.lesson2.model.YANDEX_API_URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val weatherAPI = Retrofit.Builder()
        .baseUrl(YANDEX_API_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())) // TODO setLenient()
        .client(createOkHttpClient(WeatherApiInterceptor()))
        .build().create(WeatherAPI::class.java)

    inner class WeatherApiInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(interceptor)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }

    fun getWeatherDetails(lat:Double,lon:Double, callback: Callback<WeatherDTO>) {
        weatherAPI.getWeather(YANDEX_API_KEY_VALUE,lat,lon).enqueue(callback)
    }
}