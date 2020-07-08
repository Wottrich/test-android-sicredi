package wottrich.github.io.eventcheckin.archive

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.StringRes
import wottrich.github.io.eventcheckin.R

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 05/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
fun Context.showAlert (@StringRes title: Int = R.string.default_title_error, @StringRes message: Int, callback: (() -> Unit)? = null) {
    val alert = AlertDialog.Builder(this)
    alert.setTitle(title)
    alert.setMessage(message)
    alert.setNeutralButton("OK") { _, _ -> callback?.invoke() }
    alert.show()
}