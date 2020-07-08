package wottrich.github.io.eventcheckin.viewModel

import android.os.Build
import android.os.Bundle
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.data.datasourse.EventDataSource
import wottrich.github.io.eventcheckin.model.Event

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 06/07/20
 *
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.P])
class EventDetailViewModelTest {

    //sut
    private lateinit var sut: EventDetailViewModel

    //mocks
    @Mock
    private lateinit var mockExtras: Bundle
    @Mock
    private lateinit var mockService: EventDataSource

    //dummy
    private val dummyEvent: Event
        get() = Event(
            1534784400000, "desc", "imageurl",
            -51.2146267, -30.0392981, 0.0,
            "title", "1", listOf(), listOf()
        )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = EventDetailViewModel(mockService, Unconfined)
    }

    @Test
    fun `should reload sut with default parameters` () {
        sut = EventDetailViewModel()
    }

    @Test
    fun `given no event id when request event detail then should request nothing` () = runBlocking{

        //given
        val bundle: Bundle? = null

        //when
        sut.loadEventDetails(bundle)

        //then
        verifyNoMoreInteractions(mockService)

    }

    @Test
    fun `given event id when request event detail then should update details` () = runBlocking {
        val expected = dummyEvent

        doAnswer {
            assertThat(sut.isLoading.value, equalTo(true))
            assertThat(sut.onError.value, nullValue())
            assertThat(sut.event.value, nullValue())
            assertThat(sut.successCheckIn.value, nullValue())

            expected
        }.whenever(mockService).loadEventDetail(anyString())

        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, nullValue())

        val defaultId = "1"

        //given
        whenever(mockExtras.getString(EventsListViewModel.KEY_EXTRA_EVENT_ID)) doReturn defaultId

        //then
        sut.loadEventDetails(mockExtras)

        verify(mockService).loadEventDetail(defaultId)

        assertThat(sut.isLoading.value, equalTo(false))
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, equalTo(expected))
        assertThat(sut.successCheckIn.value, nullValue())

    }

    @Test
    fun `given event id when request event detail then should return error` () = runBlocking {
        val expected = R.string.event_detail_error_message

        doAnswer {
            assertThat(sut.isLoading.value, equalTo(true))
            assertThat(sut.onError.value, nullValue())
            assertThat(sut.event.value, nullValue())
            assertThat(sut.successCheckIn.value, nullValue())

            expected
        }.whenever(mockService).loadEventDetail(anyString())

        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, nullValue())

        val defaultId = "1"

        //given
        whenever(mockExtras.getString(EventsListViewModel.KEY_EXTRA_EVENT_ID)) doReturn defaultId

        //when
        sut.loadEventDetails(mockExtras)

        //then
        verify(mockService).loadEventDetail(defaultId)

        assertThat(sut.isLoading.value, equalTo(false))
        assertThat(sut.onError.value, equalTo(expected))
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, nullValue())
    }

    @Test
    fun `given event id null when request event detail then should return error` () = runBlocking {

        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, nullValue())

        //given
        val eventId: String? = null
        whenever(mockExtras.getString(EventsListViewModel.KEY_EXTRA_EVENT_ID)) doReturn eventId

        //when
        sut.loadEventDetails(mockExtras)

        //then
        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, equalTo(R.string.event_detail_error_message))
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, nullValue())

    }

    @Test
    fun `given name and event when request check-in then should return successful` () = runBlocking {
        val expected = true

        doAnswer {
            assertThat(sut.isLoading.value, equalTo(true))
            assertThat(sut.onError.value, nullValue())
            assertThat(sut.event.value, nullValue())
            assertThat(sut.successCheckIn.value, nullValue())

            expected
        }.whenever(mockService).sendEventCheckIn("name", "email", "1")

        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, nullValue())

        //given
        val defaultId = "1"
        whenever(mockExtras.getString(EventsListViewModel.KEY_EXTRA_EVENT_ID)) doReturn defaultId
        sut.loadEventDetails(mockExtras)

        //when
        sut.confirmCheckIn("name", "email")

        //then
        verify(mockService, times(1)).sendEventCheckIn("name", "email", "1")

        assertThat(sut.isLoading.value, equalTo(false))
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, equalTo(expected))
    }

    @Test
    fun `given name and email without eventId when request check-in then should return failure` () = runBlocking {

        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, nullValue())

        //given
        val name = "name"
        val email = "email"

        //when
        sut.confirmCheckIn(name, email)

        //then
        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.event.value, nullValue())
        assertThat(sut.successCheckIn.value, equalTo(false))

    }
}