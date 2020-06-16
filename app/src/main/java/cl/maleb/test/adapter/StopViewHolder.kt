package cl.maleb.test.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.maleb.test.R
import cl.maleb.test.activity.RoutesActivity
import cl.maleb.test.data.Stops
import kotlinx.android.synthetic.main.item_stops.view.*

class StopViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(stops: Stops?) {
        if (stops != null) {
            itemView.txt_stop_id.text = stops.stop_id
            itemView.txt_stop_name.text = stops.stop_name
            itemView.txt_stop_code.text = stops.stop_code
            itemView.setOnClickListener(View.OnClickListener {
                val stopCode = itemView.txt_stop_code.text.toString()
                val intent = Intent(it.context, RoutesActivity::class.java).putExtra("stop_code", stopCode)
                it.context.startActivity(intent)
            })
        }
    }

    companion object {
        fun create(parent: ViewGroup): StopViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_stops, parent, false)
            return StopViewHolder(view)
        }
    }

}