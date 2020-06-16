package cl.maleb.test.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cl.maleb.test.R
import cl.maleb.test.adapter.StopListAdapter
import cl.maleb.test.data.State
import cl.maleb.test.viewModel.StopListViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var stopViewModel: StopListViewModel
    private lateinit var stopListAdapter: StopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopViewModel = ViewModelProvider(this).get(StopListViewModel::class.java)

        initAdapter()
        initState()

    }

    private fun initAdapter() {
        stopListAdapter = StopListAdapter { stopViewModel.retry() }
        recycler_view.adapter = stopListAdapter
        stopViewModel.stopList.observe(this,
            Observer {
                stopListAdapter.submitList(it)
            })

    }

    private fun initState() {
        txt_error.setOnClickListener { stopViewModel.retry() }
        stopViewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (stopViewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (stopViewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!stopViewModel.listIsEmpty()) {
                stopListAdapter.setState(state ?: State.DONE)
            }
        })
    }


}