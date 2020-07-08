package wottrich.github.io.eventcheckin.data.datasourse

import android.os.Build
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import wottrich.github.io.eventcheckin.data.api.INetworkAPI
import wottrich.github.io.eventcheckin.model.Event
import java.lang.Exception
import kotlin.math.exp

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 06/07/20
 *
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 */

@Config(sdk = [Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class EventDataSourceTest {

    //sut
    private lateinit var sut: EventDataSource

    //mock
    @Mock
    private lateinit var apiMock:INetworkAPI

    private val dummyEvent: Event
        get() = Event(
            1534784400000, "desc", "imageurl",
            -51.2146267, -30.0392981, 0.0,
            "title", "1", listOf(), listOf()
        )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.sut = EventDataSource(apiMock)
    }

    @Test
    fun `should load sut with default properties` () {
        this.sut = EventDataSource()
        assertThat(sut, notNullValue())
    }

    @Test
    fun `given service request when event requested then should return event list` () = runBlocking {
        val expected = listOf(dummyEvent, dummyEvent)

        //given
        whenever(sut.loadEvents()) doReturn expected

        //when
        val result = sut.loadEvents()

        //then
        verify(apiMock).getEvents()
        assertThat(expected, equalTo(result))
    }

    @Test(expected = Throwable::class)
    fun `given service request when event requested then should return error` () = runBlocking {
        val expected = Throwable("dummy")

        //given
        whenever(sut.loadEvents()) doThrow expected

        //when
        try {
            sut.loadEvents()
        } catch (e: Exception) {
            //then
            verify(apiMock).getEvents()
            assertThat(e.message, equalTo(expected.message))
        }

        Unit
    }

    @Test
    fun `given a event id when event detail requested then should return event detail` () = runBlocking {
        val expected = dummyEvent

        //load expected
        whenever(sut.loadEventDetail(anyString())) doReturn expected

        //given
        val eventId = "10"

        //when
        val result = sut.loadEventDetail(eventId)

        //then
        verify(apiMock).getEventDetail("10")
        assertThat(expected, equalTo(result))
    }

    @Test(expected = Throwable::class)
    fun `given a event id when event detail requested then should return error` () = runBlocking {
        val expected = Throwable("dummy")

        //load expected
        whenever(sut.loadEventDetail(anyString())) doThrow expected

        //given
        val eventId = "10"

        //when
        try {
            sut.loadEventDetail(eventId)
        } catch (e: Exception) {

            //then
            verify(apiMock).getEventDetail("10")
            assertThat(e.message, equalTo(expected.message))

        }

        Unit
    }

    @Test
    fun `given name, email and eventId when check in requested then should return successful` () = runBlocking {
        val expected: Any? = Any()

        //load expected
        whenever(sut.sendEventCheckIn("", "", "")) doReturn expected

        //given
        val name = "Lucas"
        val email = "lucas@gmail.com"
        val eventId = "10"

        //result
        val result = sut.sendEventCheckIn(name, email, eventId)

        //then
        val body = HashMap<String, Any>().apply {
            "name" to name
            "email" to email
            "eventId" to eventId
        }
        verify(apiMock).postEventCheckIn(body)
        assertThat(expected, equalTo(result))
    }

    @Test(expected = Throwable::class)
    fun `given name, email and eventId when check in requested then should return error` () = runBlocking {
        val expected = Throwable("dummy")

        //load expected
        whenever(sut.sendEventCheckIn("", "", "")) doThrow expected

        //given
        val name = "Lucas"
        val email = "lucas@gmail.com"
        val eventId = "10"

        //result
        try {
            sut.sendEventCheckIn(name, email, eventId)
        } catch (e: Exception) {
            //then
            val body = HashMap<String, Any>().apply {
                "name" to name
                "email" to email
                "eventId" to eventId
            }
            verify(apiMock).postEventCheckIn(body)
            assertThat(e.message, equalTo(expected.message))
        }

        Unit
    }

}