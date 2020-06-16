package cl.maleb.test.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable


class StopDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int, Stops>() {

    val stopDataSourceLiveData = MutableLiveData<StopDataSource>()

    override fun create(): DataSource<Int, Stops> {
        val stopDataSource = StopDataSource(networkService, compositeDisposable)
        stopDataSourceLiveData.postValue(stopDataSource)
        return stopDataSource
    }
}