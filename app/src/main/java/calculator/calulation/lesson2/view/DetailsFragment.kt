package calculator.calulation.lesson2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import calculator.calulation.lesson2.databinding.FragmentDetailsBinding
import calculator.calulation.lesson2.databinding.FragmentMainBinding

class DetailsFragment:Fragment() {

    companion object{
        val KEY_WEATHER:String  ="key"
        fun newInstance(bundle:Bundle):DetailsFragment{
            val fragment=DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    var _binding: FragmentDetailsBinding? = null
    private val binding:  FragmentDetailsBinding
        get() : FragmentDetailsBinding {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val weather = arguments?.getParcelable(KEY_WEATHER) as? Weather
        if(weather!=null)
        setData(weather)
    }

    private fun setData(weather: Weather) {
       binding.cityCoordinates.text =
           "${weather.city.lat} ${weather.city.long}"
       binding.cityName.text = weather.city.city
       binding.feelsLikeValue.text = weather.temerature.toString()
       binding.temperatureValue.text = weather.feelsLike.toString()
   }
}