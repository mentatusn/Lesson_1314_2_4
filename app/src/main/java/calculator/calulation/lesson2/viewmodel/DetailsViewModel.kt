package calculator.calulation.lesson2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import calculator.calulation.lesson2.model.WeatherDTO
import calculator.calulation.lesson2.repository.DetailsRepositoryImpl
import calculator.calulation.lesson2.repository.RemoteDataSource
import calculator.calulation.utils.convertWeatherDtoToModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class DetailsViewModel(
    private val liveDataObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsRepositoryImpl = DetailsRepositoryImpl(RemoteDataSource())
) : ViewModel() {
    fun getLiveData() = liveDataObserver


    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        liveDataObserver.postValue(AppState.Loading)
        repository.getWeatherDetailsFromServer(lat,lon, callBack)
    }

    private val callBack = object : Callback<WeatherDTO> {


        override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
            val serverResponse: WeatherDTO? = response.body()
            if (response.isSuccessful && serverResponse != null) {
                liveDataObserver.postValue(AppState.Success(convertWeatherDtoToModel(serverResponse))) // FIXME проверок добавить
            } else {
                //TODO("Ответ нас не устраивает")
            }
        }

        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
            TODO("Not yet implemented")
        }
    }
}