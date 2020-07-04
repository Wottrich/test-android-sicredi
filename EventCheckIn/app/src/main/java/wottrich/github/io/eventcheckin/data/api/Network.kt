package wottrich.github.io.eventcheckin.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addLoggingInterceptor()
            .build()

    val api: INetworkAPI
        get() = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(INetworkAPI::class.java)

}