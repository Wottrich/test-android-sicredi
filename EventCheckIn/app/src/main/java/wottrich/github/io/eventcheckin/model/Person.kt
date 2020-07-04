package wottrich.github.io.eventcheckin.model

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 03/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
 data class Person (
    val id: String,
    val eventId: String,
    val name: String,
    val picture: String
)