package cl.maleb.test.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import cl.maleb.test.data.*
import io.reactivex.disposables.CompositeDisposable


class StopListViewModel : ViewModel() {
    private val networkService = NetworkService.getService()
    var stopList: LiveData<PagedList<Stops>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private val stopDataSourceFactory: StopDataSourceFactory

    init {
        stopDataSourceFactory = StopDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        stopList = LivePagedListBuilder(stopDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        stopDataSourceFactory.stopDataSourceLiveData,
        StopDataSource::state
    )

    fun retry() {
        stopDataSourceFactory.stopDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return stopList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}