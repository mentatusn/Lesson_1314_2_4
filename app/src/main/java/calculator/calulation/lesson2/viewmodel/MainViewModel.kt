package calculator.calulation.lesson2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val liveDataObserver :MutableLiveData<Any> = MutableLiveData()) :ViewModel() {
    fun getLiveData()=liveDataObserver

    fun getDataFromLocalSource(){
        Thread{
            sleep(1000)
            liveDataObserver.postValue(Any())
        }.start()
    }
}