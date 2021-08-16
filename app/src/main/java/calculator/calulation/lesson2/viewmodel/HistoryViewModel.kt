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


class HistoryViewModel(
    private val liveDataObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepositoryImpl = LocalRepositoryImpl(App.getHistoryDao())) : ViewModel() {
    fun getLiveData() = liveDataObserver


    fun getAllHistory(){
        liveDataObserver.value = AppState.Loading
        liveDataObserver.value = AppState.Success(historyRepository.getAllHistory())
    }

    fun deleteByName(name:String) {
        //liveDataObserver.postValue(AppState.Loading)
        historyRepository.deleteEntityByName(name)
        liveDataObserver.value = AppState.Success(historyRepository.getAllHistory())
    }
}