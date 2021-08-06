package calculator.calulation.lesson2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.databinding.FragmentMainBinding
import calculator.calulation.lesson2.model.Weather
import calculator.calulation.lesson2.view.details.DetailsFragment
import calculator.calulation.lesson2.viewmodel.AppState
import calculator.calulation.lesson2.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    /*val mainFragmentAdapter: MainFragmentAdapter = MainFragmentAdapter()*/
    private val mainFragmentAdapter: MainFragmentAdapter =
        MainFragmentAdapter(object : OnItemViewClickListener {
            /*Вариант обработки клика без использования apply
            override fun onItemViewClick(weather: Weather) {
                val manager = activity?.supportFragmentManager
                if(manager!=null){

                    val bundle = Bundle()
                    bundle.putParcelable(DetailsFragment.KEY_WEATHER,weather)
                    manager.beginTransaction()
                        .add(R.id.container, DetailsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }*/

            override fun onItemViewClick(weather: Weather) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                            putParcelable(DetailsFragment.KEY_WEATHER, weather)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })

    /* Вариант с отложенной инициализацией вместо lazy
    lateinit var viewModel: MainViewModel*/
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() :FragmentMainBinding {
            return _binding!!
        }
    private var isRussian: Boolean = true

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mainFragmentAdapter.removeListener()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding) {
            with(viewModel) {
                mainFragmentFAB.setOnClickListener { initListener() }
                /*Инициализация viewModel, если не использовать lazy
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)*/
                /*val observer = Observer<Any>{ Toast.makeText(context,"Работает ",Toast.LENGTH_LONG).show()}*/
                getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
                getWeatherFromLocalSourceRussian()
                mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                isRussian = true
            }
        }
    }

    private fun initListener() {
        with(viewModel) {
            with(binding) {
                if (isRussian) {
                    getWeatherFromLocalSourceWorld()
                    mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                } else {
                    getWeatherFromLocalSourceRussian()
                    mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                }
                isRussian = !isRussian
            }
        }
    }

    fun View.hW(resourceID: Int, duration: Int) {
        Snackbar.make(this, requireActivity().resources.getString(resourceID), duration).show()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> TODO() //show errors
            is AppState.Success -> {
                with(binding) {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    mainFragmentRecyclerView.adapter = mainFragmentAdapter
                    mainFragmentAdapter.setWeather(appState.dataWeather)
                    root.hW(R.string.app_name, Snackbar.LENGTH_LONG)
                    /* Классический вариант использования Snackbar
                    Snackbar.make(binding.root,"Success",Snackbar.LENGTH_LONG).show()*/
                    /*setData(appState)*/
                }
            }
            AppState.Loading -> {
                binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
        }
    }
}