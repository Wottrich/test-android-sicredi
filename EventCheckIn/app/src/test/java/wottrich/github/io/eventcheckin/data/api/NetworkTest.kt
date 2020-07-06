package wottrich.github.io.eventcheckin.data.api

import android.os.Build
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 05/07/20
 *
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 */


@Config(sdk = [Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class NetworkTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp () {
        mockWebServer = MockWebServer()
    }

    @Test
    fun `when okHttpClient requested then should build client interceptor correctly` () {
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse())

        val okHttpClient = Network.okHttpClient

        //if have interceptor
//        val interceptors = okHttpClient.networkInterceptors()
//        assertThat(interceptors.filterIsInstance<HttpLoggingInterceptor>(), hasSize(1))

        okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()

        val url = mockWebServer.takeRequest().requestUrl

        //if url not null
        assertThat(url, notNullValue())

    }

}