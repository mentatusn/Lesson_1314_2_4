package calculator.calulation.lesson2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import calculator.calulation.lesson2.app.App
import calculator.calulation.lesson2.model.Weather
import calculator.calulation.lesson2.model.WeatherDTO
import calculator.calulation.lesson2.repository.DetailsRepositoryImpl
import calculator.calulation.lesson2.repository.LocalRepository
import calculator.calulation.lesson2.repository.LocalRepositoryImpl
import calculator.calulation.lesson2.repository.RemoteDataSource
import calculator.calulation.lesson2.room.HistoryDao
import calculator.calulation.utils.convertWeatherDtoToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsViewModel(
    private val liveDataObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepository: DetailsRepositoryImpl = DetailsRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDao())) : ViewModel() {
    fun getLiveData() = liveDataObserver

    fun saveWeatherToDB(weather: Weather){
        historyRepository.saveEntity(weather)
    }

    fun getWeatherFromRemoteSource(lat: Double, lon: Double) {
        liveDataObserver.postValue(AppState.Loading)
        detailsRepository.getWeatherDetailsFromServer(lat,lon, callBack)
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