package wottrich.github.io.eventcheckin.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("events")
    suspend fun getEvents () : List<Event>

    @GET("events/{event_id}")
    suspend fun getEventDetail (@Path("event_id") eventId: String) : Event

    @POST("checkin")
    suspend fun postEventCheckIn (@Body body: HashMap<String, Any>) : Any?

}