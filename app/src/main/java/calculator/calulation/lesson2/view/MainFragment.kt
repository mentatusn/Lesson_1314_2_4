package calculator.calulation.lesson2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.databinding.ActivityMainBinding
import calculator.calulation.lesson2.databinding.MainFragmentBinding
import calculator.calulation.lesson2.viewmodel.AppState
import calculator.calulation.lesson2.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    //var _binding: MainFragmentBinding? = null
    lateinit var binding:MainFragmentBinding
    /*    get() :MainFragmentBinding{
            return _binding!!
        }*/

    companion object {
        fun newInstance()= MainFragment()
    }
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //val observer = Observer<Any>{ Toast.makeText(context,"Работает ",Toast.LENGTH_LONG).show()}
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWaether()
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Error -> TODO() //show errors
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(binding.mainView,"Success",Snackbar.LENGTH_LONG).show()
                setData(appState)
            }
            AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun setData(appState: AppState.Success) {
        binding.cityCoordinates.text =
            "${appState.dataWeather.city.lat} ${appState.dataWeather.city.long}"
        binding.cityName.text = appState.dataWeather.city.city
        binding.feelsLikeValue.text = appState.dataWeather.temerature.toString()
        binding.temperatureValue.text = appState.dataWeather.feelsLike.toString()
    }


}