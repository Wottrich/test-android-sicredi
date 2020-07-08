package wottrich.github.io.eventcheckin.archive

import java.lang.Exception
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 07/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
 fun Double?.formattedPrice () : String {
    if (this == null) {
        return "Valor não informado"
    }

    val formatter = NumberFormat.getCurrencyInstance(projectLocale)
    if (formatter is DecimalFormat) {
        formatter.isDecimalSeparatorAlwaysShown = true
    }

    return formatter.format(this)
}

fun Long?.formatDate (
    withLabel: Boolean = true,
    labelMessage: String = "Data do evento:",
    nullValueMessage: String = "Data não informada"
) : String {
    if (this == null) {
        return nullValueMessage
    }

    val formatter = SimpleDateFormat("dd/MM/yyyy", projectLocale)
    val dateConverted = Date(this)

    return try {
        val dateFormatted = formatter.format(dateConverted)
        if (withLabel) "$labelMessage $dateFormatted"
        else dateFormatted
    } catch (e: Exception) {
        nullValueMessage
    }

}