package calculator.calulation.lesson2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import calculator.calulation.lesson2.app.App
import calculator.calulation.lesson2.repository.LocalRepositoryImpl


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