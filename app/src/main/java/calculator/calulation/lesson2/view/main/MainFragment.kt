package calculator.calulation.lesson2.view.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.databinding.FragmentMainBinding
import calculator.calulation.lesson2.model.City
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
                openDetailsFragment(weather)
            }
        })

    private fun openDetailsFragment(weather: Weather) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .add(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                    putParcelable(DetailsFragment.KEY_WEATHER, weather)
                }))
                .addToBackStack("")
                .commit()
        }
    }


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

    private fun showRatio() {
        showDialog(getString(R.string.dialog_rationale_title) ,getString(R.string.dialog_rationale_meaasge))
    }


    private fun showDialog(title:String,message: String) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Пускай работает корректно") { _, _ ->
                    requestPermission()
                }
                .setNegativeButton("Пускай не работает") { dialog, _ ->
                    dialog.dismiss()
                    requireActivity().finish()
                }
                .create()
                .show()
        }
    }

    private fun showAddressDialog(message: String, location: Location) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.dialog_address_title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_address_get_weather) { _, _ ->
                    openDetailsFragment(Weather(City(message,location.latitude,location.longitude)))
                }
                .setNegativeButton(R.string.dialog_button_close) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private val REQUEST_CODE = 1
    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }

    private fun checkPermission() {
        context?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PermissionChecker.PERMISSION_GRANTED
            ) {
                getLocation()
            } else {

                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showRatio()
                } else {
                    requestPermission()
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    getLocation()
                } else {
                    context?.let { showRatio() }
                }
            }
        }
    }

    /*private fun getAddress(context: Context,location: Location){ // TODO Async
        val geoCoder = Geocoder(context)
        val address = geoCoder.getFromLocation(location.latitude,location.longitude,1)
        showAddressDialog(address[0].getAddressLine(0),location)
    }  */

    private fun getAddress(context: Context,location: Location){ 
        val handler = Handler(requireActivity().mainLooper)


        Thread{
            val geoCoder = Geocoder(context)
            val address = geoCoder.getFromLocation(location.latitude,location.longitude,1)
            handler.post { showAddressDialog(address[0].getAddressLine(0),location) }
        }.start()

    }

    private val REFRESH_PERIOD = 6000L
    private val MINIMAL_DISTANCE = 100f

    private val locationListener = object : LocationListener{
        override fun onLocationChanged(location: Location) {
            getAddress(requireActivity(),location)
        }

    }

    private fun getLocation(){
        activity?.let { context->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PermissionChecker.PERMISSION_GRANTED
            ){
                val locationManager  = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let{
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            locationListener
                        )
                    }
                }else{

                    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if(location==null) {
                        //TODO почему не работает
                        showDialog(getString(R.string.dialog_title_gps_turned_off),getString(R.string.dialog_message_last_location_unknown))
                    }else{
                        getAddress(context,location)
                    }
                }
            }else{
                showRatio()
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding) {
            with(viewModel) {
                mainFragmentFAB.setOnClickListener { initListener() }
                mainFragmentFABLocation.setOnClickListener { checkPermission() }
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
                    mainFragmentAdapter.setWeather(appState.weatherData)
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