package wottrich.github.io.eventcheckin.model

import com.google.gson.annotations.SerializedName
import wottrich.github.io.eventcheckin.archive.projectLocale
import java.lang.Exception
import java.text.DecimalFormat
import java.text.NumberFormat
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

    fun formattedPrice () : String {
        if (price == null) {
            return "Valor não informado"
        }

        val formatter = NumberFormat.getCurrencyInstance(projectLocale)
        if (formatter is DecimalFormat) {
            formatter.isDecimalSeparatorAlwaysShown = true
        }

        return formatter.format(price)
    }

    fun formatDate (withLabel: Boolean = true) : String {
        if (date == null) {
            return ""
        }

        val formatter = SimpleDateFormat("dd/MM/yyyy", projectLocale)
        val dateConverted = Date(date)

        return try {
            val dateFormatted = formatter.format(dateConverted)
            if (withLabel) "Data do evento: $dateFormatted"
            else dateFormatted
        } catch (e: Exception) {
            "Data não informada"
        }

    }

    fun numberOfPeople () : String {
        return "${people?.size ?: 0} pessoa(s)"
    }

    override fun toString(): String {
        return "Evento: $title \nDescrição do evento:\n$description"
    }

}