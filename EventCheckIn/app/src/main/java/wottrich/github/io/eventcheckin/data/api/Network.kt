package wottrich.github.io.eventcheckin.data.api

import okhttp3.OkHttpClient
import wottrich.github.io.eventcheckin.BuildConfig

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 03/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */

object Network {

    val okHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addLoggingInterceptor()
            }
            return builder.build()
        }

}