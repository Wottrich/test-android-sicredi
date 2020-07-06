package wottrich.github.io.eventcheckin.archive

import android.content.Intent
import android.net.Uri

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 05/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
fun Intent.shareText (textToShare: String): Intent {
    return this.apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, textToShare)
    }
}

fun Intent.openMap (latitude: Double, longitude: Double): Intent {
    val geoLocation = "geo:$latitude,$longitude"
    return this.apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(geoLocation)
    }
}