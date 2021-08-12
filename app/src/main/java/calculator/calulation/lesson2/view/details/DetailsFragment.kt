package calculator.calulation.lesson2.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import calculator.calulation.lesson2.databinding.FragmentDetailsBinding
import calculator.calulation.lesson2.model.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import kotlin.concurrent.thread

class DetailsFragment : Fragment() {

    companion object {
        const val KEY_WEATHER: String = "key"
        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() : FragmentDetailsBinding {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    fun renderData(weather:WeatherDTO){
        binding.mainView.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE

        weatherBundle?.let{ weatherBundle:Weather->
            binding.cityCoordinates.text = "${weatherBundle.city.lat} ${weatherBundle.city.lon}"
            binding.cityName.text = weatherBundle.city.name
            binding.feelsLikeValue.text = weather.fact.temp.toString()
            binding.temperatureValue.text =weather.fact.feels_like.toString()
            binding.condition.text = weather.fact.condition
        }

    }

    var weatherBundle:Weather? = null

    fun getWeather(){
        binding.mainView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        weatherBundle?.let{
            val client = OkHttpClient()
            val builder: Request.Builder  =Request.Builder()
            builder.header(YANDEX_API_KEY_NAME, YANDEX_API_KEY_VALUE)
            builder.url("$YANDEX_API_URL$YANDEX_API_URL_END_POINT?lat${it.city.lat}&lon${it.city.lon}")

            val request:Request = builder.build()
            val call: Call =client.newCall(request)

            //асинхронный запрос
            call.enqueue( object :Callback{
                override fun onResponse(call: Call, response: Response) {
                     val serverResponse:String? = response.body()?.string()
                     if(response.isSuccessful&&serverResponse!=null){
                         requireActivity().runOnUiThread(Runnable {
                             renderData(Gson().fromJson(serverResponse,WeatherDTO::class.java))
                         })
                     }else{
                        //TODO("Ответ нас не устраивает")
                     }
                }
                override fun onFailure(call: Call, e: IOException) {
                    //TODO("Не удалось связаться с сервером")
                }
            })

            /*Thread {
                // action1
                val response:Response = call.execute()
                val serverResponse:String? = response.body()?.string()
                requireActivity().runOnUiThread(Runnable {
                    renderData(Gson().fromJson(serverResponse,WeatherDTO::class.java))
                })
                // action2
            }.start()*/

            //Log
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Weather>(KEY_WEATHER)?.apply {
            weatherBundle = this
            //WeatherLoader(this@DetailsFragment, city.lat, city.lon).loadWeather()
            getWeather()
        }
    }
}