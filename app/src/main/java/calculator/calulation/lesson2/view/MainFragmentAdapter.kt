package calculator.calulation.lesson2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import calculator.calulation.lesson2.R

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    // private var weatherData: List<Weather> = listOf()
    private lateinit var weatherData: List<Weather>

    fun setWeather(list: List<Weather>){
        weatherData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = (LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_recycler_item,parent,false))
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.init(weatherData[position])
        //(" разобраться с payloads: MutableList<Any>")
    }


    override fun getItemCount(): Int {
        return weatherData.size;
    }

    class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun init(weather: Weather){
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                weather.city.city
            Toast.makeText(itemView.context,weather.city.city,Toast.LENGTH_SHORT).show()
        }
    }

}