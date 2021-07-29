package calculator.calulation.lesson2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import calculator.calulation.lesson2.model.Repository
import calculator.calulation.lesson2.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveDataObserver :MutableLiveData<AppState> = MutableLiveData(),
                    val repository: Repository = RepositoryImpl()) :ViewModel() {
    fun getLiveData()=liveDataObserver

    fun getWeatherFromLocalSourceRussian()=getDataFromLocalSource(true)
    fun getWeatherFromLocalSourceWorld()=getDataFromLocalSource(false)

    fun getWeatherFromRemoteSource()=getDataFromLocalSource(true)

    fun getDataFromLocalSource(isRussian: Boolean){
        Thread{
            liveDataObserver.postValue(AppState.Loading)
            sleep(2000)
            liveDataObserver.postValue(AppState.Success(
                if(isRussian) repository.getWeatherFromLocalRussian() else
                    repository.getWeatherFromLocalWorld()))
        }.start()
    }
}