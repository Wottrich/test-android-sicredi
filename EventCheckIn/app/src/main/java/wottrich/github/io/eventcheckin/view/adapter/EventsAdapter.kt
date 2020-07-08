package wottrich.github.io.eventcheckin.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.archive.formatDate
import wottrich.github.io.eventcheckin.model.Event

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 04/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
class EventsAdapter(
    context: Context,
    private val items: LiveData<List<Event>>,
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<EventsViewHolder>() {

    private var onItemClick: ((id: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            DataBindingUtil.inflate(layoutInflater, R.layout.row_event, parent, false)
        )
    }

    override fun getItemCount(): Int = items.value.orEmpty().size

    private fun getItem(position: Int) : Event? {
        return items.value?.get(position)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = getItem(position) ?: return

        holder.binding.apply {
            title = event.title ?: "Nome do evento não informado"
            date = event.date.formatDate()
            description = (event.description ?: "").replace("\n", "")
            showDescription = event.description != null && event.description.isNotEmpty()
        }

        holder.setOnClick {
            val eventId = getItem(it)?.id ?: return@setOnClick
            onItemClick?.invoke(eventId)
        }

        if (!event.image.isNullOrEmpty()) {
            holder.bind(event.image)
        }
    }

    fun setOnClick(hof: (id: String) -> Unit) {
        onItemClick = hof
    }

}