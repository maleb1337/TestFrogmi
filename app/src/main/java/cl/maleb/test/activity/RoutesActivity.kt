package cl.maleb.test.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.maleb.test.R
import cl.maleb.test.adapter.RouteListAdapter
import cl.maleb.test.data.State
import cl.maleb.test.viewModel.RouteListViewModel
import kotlinx.android.synthetic.main.activity_main.*


class RoutesActivity : AppCompatActivity() {

    private lateinit var routeViewModel: RouteListViewModel
    private lateinit var routeListAdapter: RouteListAdapter
    var stopCode: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)
        stopCode = intent.getStringExtra("stop_code")
        //TODO: Simple GET request to routes.
        routeViewModel = ViewModelProvider(this).get(RouteListViewModel::class.java)

        initAdapter()
        initState()
    }

    companion object {
        lateinit var selected_stop_code: String
    }

    init {
        selected_stop_code = stopCode.toString()
    }

    private fun initAdapter() {
        routeListAdapter = RouteListAdapter { routeViewModel.retry() }
        recycler_view.adapter = routeListAdapter
        routeViewModel.routeList.observe(this,
            Observer {
                routeListAdapter.submitList(it)
            })

    }

    private fun initState() {
        txt_error.setOnClickListener { routeViewModel.retry() }
        routeViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (routeViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (routeViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!routeViewModel.listIsEmpty()) {
                routeListAdapter.setState(state ?: State.DONE)
            }
        })
    }

}