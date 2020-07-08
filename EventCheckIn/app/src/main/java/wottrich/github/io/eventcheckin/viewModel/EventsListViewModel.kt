package wottrich.github.io.eventcheckin.viewModel

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.data.api.Network
import wottrich.github.io.eventcheckin.data.datasourse.EventDataSource
import wottrich.github.io.eventcheckin.model.Event
import wottrich.github.io.eventcheckin.view.EventDetailActivity
import wottrich.github.io.eventcheckin.view.EventsListActivity
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 04/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
class EventsListViewModel (
    private val service: EventDataSource = EventDataSource(),
    context: CoroutineContext = IO
) : BaseViewModel(context) {

    companion object {
        const val KEY_EXTRA_EVENT_ID = "keyEventId_EventsListActivityToEventDetailActivity"
    }

    private val mEvents: MutableLiveData<List<Event>> = MutableLiveData()
    val events: LiveData<List<Event>>
        get() = mEvents

    private val mEventIdClicked: MutableLiveData<String> = MutableLiveData()
    val eventIdClicked: LiveData<String>
        get() = mEventIdClicked

    fun loadEvents () {
        isLoading.postValue(true)

        scope.launch {
            try {
                val result = service.loadEvents()
                mEvents.postValue(result)
            } catch (e: Exception) {
                onError.postValue(R.string.load_events_failure)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun onItemClick (eventId: String) {
        mEventIdClicked.value = eventId
    }

}