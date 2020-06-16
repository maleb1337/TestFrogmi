package cl.maleb.test.data

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("v1/stops?center_lat=-33.444087&center_lon=-70.653674")
    fun getStops(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Single<Response>


    @GET("v1/stops/PF243/stop_routes")
    fun getRoutes(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Single<Response>


    companion object {
        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.scltrans.it/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }

}