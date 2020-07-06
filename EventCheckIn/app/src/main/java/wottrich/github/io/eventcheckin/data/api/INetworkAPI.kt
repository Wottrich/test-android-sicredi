package wottrich.github.io.eventcheckin.data.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import wottrich.github.io.eventcheckin.BuildConfig
import wottrich.github.io.eventcheckin.model.Event

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 03/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */

interface INetworkAPI {

    companion object {
        val api: INetworkAPI
            get() = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(Network.okHttpClient)
                .build()
                .create(INetworkAPI::class.java)
    }

    @GET("events")
    suspend fun getEvents () : List<Event>

    @GET("events/{event_id}")
    suspend fun getEventDetail (@Path("event_id") eventId: String) : Event

    @POST("checkin")
    suspend fun postEventCheckIn (@Body body: HashMap<String, Any>) : Any?

}