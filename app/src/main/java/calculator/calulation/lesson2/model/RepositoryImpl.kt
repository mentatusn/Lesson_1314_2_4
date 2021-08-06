package calculator.calulation.lesson2.model

class RepositoryImpl : Repository {
    override fun getWeatherFromServer() = Weather()
    override fun getWeatherFromLocalRussian() = getRussianCities()
    override fun getWeatherFromLocalWorld() = getWorldCities()
}