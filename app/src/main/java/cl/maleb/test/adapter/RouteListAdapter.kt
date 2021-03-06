package cl.maleb.test.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cl.maleb.test.data.State
import cl.maleb.test.data.Routes

class RouteListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<Routes, RecyclerView.ViewHolder>(
        RouteDiffCallBack
    ) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) RouteViewHolder.create(
            parent
        ) else ListFooterViewHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            (holder as RouteViewHolder).bind(getItem(position))
        else (holder as ListFooterViewHolder).bind(state)
    }

    companion object {
        val RouteDiffCallBack = object : DiffUtil.ItemCallback<Routes>() {
            override fun areItemsTheSame(oldItem: Routes, newItem: Routes): Boolean {
                return oldItem.route_id == newItem.route_id
            }

            override fun areContentsTheSame(oldItem: Routes, newItem: Routes): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }


}