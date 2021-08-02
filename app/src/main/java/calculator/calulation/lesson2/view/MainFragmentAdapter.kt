package calculator.calulation.lesson2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import calculator.calulation.lesson2.R

class MainFragmentAdapter constructor(var onItemViewClickListener: OnItemViewClickListener?) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    // private var weatherData: List<Weather> = listOf()
    private lateinit var weatherData: List<Weather>

    fun setWeather(list: List<Weather>){
        weatherData = list
        notifyDataSetChanged()
    }

    fun removeListener(){
        onItemViewClickListener = null
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

    inner class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun init(weather: Weather){
            with(itemView){
                findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text =
                    weather.city.name
                setOnClickListener{onItemViewClickListener?.onItemViewClick(weather)}
            }
            //Toast.makeText(itemView.context,weather.city.city,Toast.LENGTH_SHORT).show()
        }
    }

}