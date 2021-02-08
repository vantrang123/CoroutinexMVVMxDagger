package com.tom.learncoroutinexroom.data.remote

import com.tom.learncoroutinexroom.data.model.Player
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    companion object {
        const val BASE_URL = "https://xapi-player.herokuapp.com/"
    }

    @GET("api/v1/player/")
    suspend fun getAllPlayers(): Response<List<Player>>
}