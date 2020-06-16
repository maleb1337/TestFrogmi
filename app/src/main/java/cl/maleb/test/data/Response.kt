package cl.maleb.test.data

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("results")
    val stops: List<Stops>,
    val routes: List<Routes>
)

data class Stops(
    val stop_id: String,
    val stop_name: String,
    val stop_code: String
)

data class Routes(
    val route_id: String,
    val direction_headsign: String
)