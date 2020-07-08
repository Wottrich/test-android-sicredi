package wottrich.github.io.eventcheckin.viewModel

import android.os.Build
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
 * @since 07/07/20
 *
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.P])
class EventsListViewModelTest {

    //sut
    private lateinit var sut: EventsListViewModel

    //mock
    @Mock
    private lateinit var mockService: EventDataSource

    //dummy
    private val dummyEvent: Event
        get() = Event(
            1534784400000, "desc", "imageurl",
            -51.2146267, -30.0392981, 0.0,
            "title", "1", listOf(), listOf()
        )

    private val dummyEventList: List<Event>
        get() = listOf(dummyEvent, dummyEvent)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = EventsListViewModel(mockService, Unconfined)
    }

    @Test
    fun `should test sut with default params and your properties` () {
        sut = EventsListViewModel()
        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.eventIdClicked.value, nullValue())
        assertThat(sut.events.value, nullValue())
    }

    @Test
    fun `when service requested then should return event list` () = runBlocking {
        val expected = dummyEventList

        doAnswer {
            assertThat(sut.isLoading.value, equalTo(true))
            assertThat(sut.onError.value, nullValue())
            assertThat(sut.eventIdClicked.value, nullValue())
            assertThat(sut.events.value, nullValue())

            expected
        }.whenever(mockService).loadEvents()

        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.eventIdClicked.value, nullValue())
        assertThat(sut.events.value, nullValue())

        //when
        sut.loadEvents()

        //then
        verify(mockService).loadEvents()
        assertThat(sut.isLoading.value, equalTo(false))
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.eventIdClicked.value, nullValue())
        assertThat(sut.events.value, equalTo(expected))

    }

    @Test
    fun `when service requested then should return failure` () = runBlocking {

        doAnswer {
            assertThat(sut.isLoading.value, equalTo(true))
            assertThat(sut.onError.value, nullValue())
            assertThat(sut.eventIdClicked.value, nullValue())
            assertThat(sut.events.value, nullValue())

            Throwable("Event list with error")
        }.whenever(mockService).loadEvents()

        assertThat(sut.isLoading.value, nullValue())
        assertThat(sut.onError.value, nullValue())
        assertThat(sut.eventIdClicked.value, nullValue())
        assertThat(sut.events.value, nullValue())

        //when
        sut.loadEvents()

        //then
        verify(mockService).loadEvents()
        assertThat(sut.isLoading.value, equalTo(false))
        assertThat(sut.onError.value, equalTo(R.string.load_events_failure))
        assertThat(sut.eventIdClicked.value, nullValue())
        assertThat(sut.events.value, nullValue())

    }

    @Test
    fun `given an event id when detail requested then should return the event id` () = runBlocking {

        //given
        val eventIdClicked = "1"

        //when
        sut.onItemClick(eventIdClicked)

        //then
        assertThat(sut.eventIdClicked.value, equalTo(eventIdClicked))

    }

}