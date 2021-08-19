package calculator.calulation.lesson2.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import calculator.calulation.lesson2.R
import calculator.calulation.lesson2.model.Weather
import kotlinx.android.synthetic.main.fragment_history_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<Weather> = arrayListOf()


    fun setData(data: List<Weather>) {
        this.data = data
        notifyDataSetChanged()
    }
    private lateinit var listener: OnClickAdapterItem;

    public fun setListener(listener:OnClickAdapterItem){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_history_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Weather) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.textViewHistory.text =
                    String.format("%s %d %s", data.city.name, data.temperature, data.condition)
                itemView.setOnClickListener {

                    //getHistoryDao().myDelete((data.city.name))

                    listener.onItemClick(data.city.name,adapterPosition)
                    Toast.makeText(
                        itemView.context,
                        "on click: ${data.city.name}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}