package cl.maleb.test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.maleb.test.R
import cl.maleb.test.data.Routes
import kotlinx.android.synthetic.main.item_routes.view.*

class RouteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(routes: Routes?) {
        if (routes != null) {
            itemView.txt_route_id.text = routes.route_id
            itemView.txt_route_name.text = routes.direction_headsign
        }
    }

    companion object {
        fun create(parent: ViewGroup): RouteViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_routes, parent, false)
            return RouteViewHolder(view)
        }
    }

}