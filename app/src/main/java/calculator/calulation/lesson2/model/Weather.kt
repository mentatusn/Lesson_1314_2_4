package calculator.calulation.lesson2.view

class Weather(val city: City= getDefaultCity(),val temerature:Int =22,val feelsLike:Int =24 )

fun getDefaultCity() = City("Москва",55.75,37.61)

data class City( val city:String,val lat:Double, val long:Double)

