package wottrich.github.io.eventcheckin.data.datasourse

import retrofit2.*
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.data.api.INetworkAPI
import wottrich.github.io.eventcheckin.model.Event

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 03/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */

class EventDataSource(
    private val api: INetworkAPI
) {

    suspend fun loadEvents(): List<Event>? {
        return api.getEvents()
    }

    suspend fun loadEventDetail(id: String): Event? {
        return api.getEventDetail(id)
    }

    suspend fun sendEventCheckIn(name: String, email: String, eventId: String): Any? {
        val body = HashMap<String, Any>().apply {
            "name" to name
            "email" to email
            "eventId" to eventId
        }
        return api.postEventCheckIn(body)
    }

}