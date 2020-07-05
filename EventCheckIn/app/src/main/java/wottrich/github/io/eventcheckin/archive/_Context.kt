package wottrich.github.io.eventcheckin.archive

import android.app.AlertDialog
import android.content.Context

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 05/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
fun Context.showAlert (title: String, message: String, callback: (() -> Unit)? = null) {
    val alert = AlertDialog.Builder(this)
    alert.setTitle(title)
    alert.setMessage(message)
    alert.setNeutralButton("OK") { _, _ -> callback?.invoke() }
    alert.show()
}