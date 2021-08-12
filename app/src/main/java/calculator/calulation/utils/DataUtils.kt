package calculator.calulation.utils

import calculator.calulation.lesson2.model.FactDTO
import calculator.calulation.lesson2.model.Weather
import calculator.calulation.lesson2.model.WeatherDTO
import calculator.calulation.lesson2.model.getDefaultCity

fun convertWeatherDtoToModel (weatherDTO: WeatherDTO):List<Weather>{ //FIXME
    val factDTO: FactDTO =weatherDTO.fact
    return listOf(Weather(getDefaultCity(),factDTO.temp,factDTO.feels_like,factDTO.condition,factDTO.icon)) //FIXME
}