package calculator.calulation.lesson2.view.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import calculator.calulation.lesson2.databinding.FragmentDetailsBinding
import calculator.calulation.lesson2.model.City
import calculator.calulation.lesson2.model.Weather
import calculator.calulation.lesson2.viewmodel.AppState
import calculator.calulation.lesson2.viewmodel.DetailsViewModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {
    private lateinit var weatherBundle: Weather
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() : FragmentDetailsBinding {
            return _binding!!
        }

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    companion object {
        const val KEY_WEATHER: String = "key"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        arguments?.getParcelable<Weather>(KEY_WEATHER)?.apply {
            weatherBundle = this
        }
        viewModel.getWeatherFromRemoteSource(weatherBundle.city.lat, weatherBundle.city.lon)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                //TODO ?????????????? ????????????
            }
            AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                setData(appState.weatherData[0]) //FIXME
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun saveCity(
        city: City,
        weather: Weather
    ) {
        viewModel.saveWeatherToDB(
            Weather(
                city,
                weather.temperature,
                weather.feelsLike,
                weather.condition
            )
        )
    }

    private fun setData(weather: Weather) {
        binding.mainView.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE

        saveCity(weatherBundle.city,weather)

        weatherBundle?.let { weatherBundle: Weather ->
            binding.cityCoordinates.text = "${weatherBundle.city.lat} ${weatherBundle.city.lon}"
            binding.cityName.text = weatherBundle.city.name
            binding.feelsLikeValue.text = weather.temperature.toString()
            binding.temperatureValue.text = weather.feelsLike.toString()
            binding.condition.text = weather.condition

            Picasso
                .get()
                .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                //.transform(CircleTransformation())
                //.rotate(90f)
                .into(binding.headerIcon)
           /* Glide.with(binding.headerIcon)
                .load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")
                .into(binding.headerIcon)*/

           /* binding.headerIcon.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")*/

            GlideToVectorYou.justLoadImage(requireActivity(),
                Uri.parse("https://yastatic.net/weather/i/icons/blueye/color/svg/${weather.icon}.svg"),
                binding.weatherIcon)


        }
    }


}