package wottrich.github.io.eventcheckin.model

import com.google.gson.annotations.SerializedName
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
    val longitude: Double?,
    val latitude: Double?,
    val price: Double?,
    val title: String?,
    val id: String?,
    val people: List<Person>?,
    @SerializedName("cupons")
    val coupons: List<Coupon>?

) {

    fun formatDate () : String {
        if (date == null) {
            return ""
        }

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateConverted = Date(date)

        return try {
            val dateFormatted = formatter.format(dateConverted)
            "Data do evento: $dateFormatted"
        } catch (e: Exception) {
            "Data não informada"
        }

    }

}