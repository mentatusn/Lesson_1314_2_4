package calculator.calulation.lesson2.viewmodel

import calculator.calulation.lesson2.view.Weather

sealed class AppState{
    //data class Success(val dataWeather: Any):AppState()
    data class Success(val dataWeather: Weather):AppState()
    data class Error( val error:Throwable):AppState()
    object Loading: AppState()
}
