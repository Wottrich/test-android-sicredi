package wottrich.github.io.eventcheckin.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.databinding.RowEventBinding
import wottrich.github.io.eventcheckin.model.Event

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 04/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
class EventsViewHolder(
    var binding: RowEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setOnClick (hof: (Int) -> Unit) {
        itemView.setOnClickListener { hof.invoke(absoluteAdapterPosition) }
    }

    fun bind (imageUrl: String) {
        Glide.with(binding.root.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_image_placeholder)
            .into(binding.ivEvent)
    }

}