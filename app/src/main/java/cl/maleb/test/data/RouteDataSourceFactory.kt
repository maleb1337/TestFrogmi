package cl.maleb.test.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable


class RouteDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int, Routes>() {

    val routeDataSourceLiveData = MutableLiveData<RouteDataSource>()

    override fun create(): DataSource<Int, Routes> {
        val routeDataSource = RouteDataSource(networkService, compositeDisposable)
        routeDataSourceLiveData.postValue(routeDataSource)
        return routeDataSource
    }
}