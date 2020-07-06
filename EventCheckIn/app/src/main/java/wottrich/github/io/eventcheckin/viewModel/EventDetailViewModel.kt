package wottrich.github.io.eventcheckin.viewModel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.data.api.Network
import wottrich.github.io.eventcheckin.data.datasourse.EventDataSource
import wottrich.github.io.eventcheckin.model.Event
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 05/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
class EventDetailViewModel(
    context: CoroutineContext = IO
) : BaseViewModel() {

    private var eventId: String? = null

    private val scope = CoroutineScope(context)

    private val mEvent: MutableLiveData<Event> = MutableLiveData()
    val event: LiveData<Event>
        get() = mEvent

    private val mSuccessCheckIn: MutableLiveData<Boolean> = MutableLiveData()
    val successCheckIn: LiveData<Boolean>
        get() = mSuccessCheckIn

    private val service: EventDataSource
        get() = EventDataSource()

    fun loadEventDetails (intent: Intent) {
        val key = EventsListViewModel.KEY_EXTRA_EVENT_ID
        if (intent.hasExtra(key)) {

            val eventId = intent.getStringExtra(key)

            if(eventId != null && !eventId.isNullOrEmpty()) {
                this.eventId = eventId
                isLoading.value = true

                scope.launch {
                    try {

                        val result = service.loadEventDetail(eventId)
                        mEvent.postValue(result)

                    } catch (e: Exception) {
                        onError.postValue(R.string.event_detail_error_message)
                    } finally {
                        isLoading.postValue(false)
                    }
                }

            } else {
                sendDetailError()
            }

        } else {
            sendDetailError()
        }

    }

    fun confirmCheckIn (name: String, email: String) {
        val eventId = this.eventId
        if (eventId != null) {
            isLoading.value = true
            scope.launch {
                try {
                    service.sendEventCheckIn(name, email, eventId)
                } catch (e: Exception) {
                    mSuccessCheckIn.postValue(false)
                } finally {
                    isLoading.postValue(false)
                }
            }
        } else {
            sendCheckInError()
        }

    }

    fun setError (errorMessage: Int) {
        onError.value = errorMessage
    }

    private fun sendDetailError () {
        onError.value = R.string.event_detail_error_message
    }

    private fun sendCheckInError () {
        mSuccessCheckIn.value = false
    }

}