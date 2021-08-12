package calculator.calulation.lesson2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import calculator.calulation.lesson2.repository.MainRepository
import calculator.calulation.lesson2.repository.MainRepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataObserver: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: MainRepository = MainRepositoryImpl()
) : ViewModel() {
    fun getLiveData() = liveDataObserver

    fun getWeatherFromLocalSourceRussian() = getDataFromLocalSource(true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        Thread {
            with(liveDataObserver) {
                with(repository) {
                    postValue(AppState.Loading)
                    sleep(2000)
                    postValue(
                        AppState.Success(
                            if (isRussian) getWeatherFromLocalRussian()
                            else getWeatherFromLocalWorld()
                        )
                    )
                }
            }
        }.start()
    }
}