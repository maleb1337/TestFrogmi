package cl.maleb.test.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import cl.maleb.test.data.*
import io.reactivex.disposables.CompositeDisposable


class RouteListViewModel : ViewModel() {
    private val networkService = NetworkService.getService()
    var routeList: LiveData<PagedList<Routes>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private val routeDataSourceFactory: RouteDataSourceFactory

    init {
        routeDataSourceFactory = RouteDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        routeList = LivePagedListBuilder(routeDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        routeDataSourceFactory.routeDataSourceLiveData,
        RouteDataSource::state
    )

    fun retry() {
        routeDataSourceFactory.routeDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return routeList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}