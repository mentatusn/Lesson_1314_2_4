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
import calculator.calulation.lesson2.databinding.FragmentMainBinding
import calculator.calulation.lesson2.databinding.MainFragmentBinding
import calculator.calulation.lesson2.viewmodel.AppState
import calculator.calulation.lesson2.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    val mainFragmentAdapter:MainFragmentAdapter =MainFragmentAdapter()

    lateinit var viewModel: MainViewModel
    var _binding: FragmentMainBinding? = null
    private val binding:FragmentMainBinding
        get() :FragmentMainBinding{
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding
    }

    companion object {
        fun newInstance()= MainFragment()
    }
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    var isRussian:Boolean = true
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.mainFragmentFAB.setOnClickListener{
            initListener()
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //val observer = Observer<Any>{ Toast.makeText(context,"Работает ",Toast.LENGTH_LONG).show()}
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRussian()
        binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        isRussian = true
    }

    private fun initListener() {
        if (isRussian) {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_earth)
        } else {
            viewModel.getWeatherFromLocalSourceRussian()
            binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
        }
        isRussian = !isRussian
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.Error -> TODO() //show errors
            is AppState.Success -> {
                binding.mainFragmentLoadingLayout.visibility = View.GONE
                binding.mainFragmentRecyclerView.adapter = mainFragmentAdapter
                mainFragmentAdapter.setWeather(appState.dataWeather)

                /*Snackbar.make(binding.mainView,"Success",Snackbar.LENGTH_LONG).show()
                setData(appState)*/
            }
            AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
        }
    }

    /*private fun setData(appState: AppState.Success) {
        binding.cityCoordinates.text =
            "${appState.dataWeather.city.lat} ${appState.dataWeather.city.long}"
        binding.cityName.text = appState.dataWeather.city.city
        binding.feelsLikeValue.text = appState.dataWeather.temerature.toString()
        binding.temperatureValue.text = appState.dataWeather.feelsLike.toString()
    }*/


}