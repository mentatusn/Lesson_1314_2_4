package calculator.calulation.lesson2.model

import calculator.calulation.lesson2.view.Weather

class RepositoryImpl:Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather();
    }

    override fun getWeatherFromLocal(): Weather {
        return Weather();
    }
}