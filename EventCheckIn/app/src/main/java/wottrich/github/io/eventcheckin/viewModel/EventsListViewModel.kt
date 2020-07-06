package wottrich.github.io.eventcheckin.viewModel

import android.content.Context
import android.content.Intent
import android.location.Location
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
    context: CoroutineContext = IO
) : BaseViewModel() {

    companion object {
        const val KEY_EXTRA_EVENT_ID = "keyEventId_EventsListActivityToEventDetailActivity"
    }

    private val scope = CoroutineScope(context)

    private val mActivityToGo: MutableLiveData<Intent> = MutableLiveData()
    val activityToGo: MutableLiveData<Intent>
        get() = mActivityToGo

    private val mEvents: MutableLiveData<List<Event>> = MutableLiveData()
    val events: MutableLiveData<List<Event>>
        get() = mEvents

    private val service: EventDataSource
        get () = EventDataSource()

    fun loadEvents () {
        isLoading.value = true

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

    fun onItemClick (context: Context, eventId: String) {
        val intent = Intent(context, EventDetailActivity::class.java)
        intent.putExtra(KEY_EXTRA_EVENT_ID, eventId)
        activityToGo.value = intent
    }

}