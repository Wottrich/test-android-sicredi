package wottrich.github.io.eventcheckin.model

import com.google.gson.annotations.SerializedName

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 03/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
data class Event(

    val date: Long?,
    val description: String?,
    val image: String?,
    val longitude: Long?,
    val latitude: Long?,
    val price: Double?,
    val title: String?,
    val id: String?,
    val people: List<Person>?,
    @SerializedName("cupons")
    val coupons: List<Coupon>?

)