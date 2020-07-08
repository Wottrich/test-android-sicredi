package wottrich.github.io.eventcheckin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.archive.showAlert
import wottrich.github.io.eventcheckin.databinding.ActivityEventsListBinding
import wottrich.github.io.eventcheckin.view.adapter.EventsAdapter
import wottrich.github.io.eventcheckin.viewModel.EventsListViewModel

class EventsListActivity : AppCompatActivity() {

    private lateinit var viewModel: EventsListViewModel
    private lateinit var binding: ActivityEventsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.events_title)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_events_list)
        viewModel = ViewModelProvider(this)[EventsListViewModel::class.java]

        setupBinding()
        setupRecycler()
        setupObservers()

        viewModel.loadEvents()
    }

    private fun setupBinding () {
        binding.apply {
            val activity = this@EventsListActivity
            val bind = this

            bind.lifecycleOwner = activity
            bind.viewModel = activity.viewModel

            bind.loading = Transformations.map(activity.viewModel.isLoading) {
                if(it) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setupRecycler () {
        binding.apply {
            val activity = this@EventsListActivity
            val bind = this

            bind.rvEvents.apply {
                adapter = EventsAdapter(activity, activity.viewModel.events).apply {
                    this.setOnClick { eventId ->
                        activity.viewModel.onItemClick(eventId)
                    }
                }
            }
        }
    }

    private fun setupObservers () {

        viewModel.eventIdClicked.observe(this, Observer {
            if (it != null) {
                val intent = Intent(this, EventDetailActivity::class.java).apply {
                    putExtra(EventsListViewModel.KEY_EXTRA_EVENT_ID, it)
                }
                startActivity(intent)
            }
        })

        viewModel.events.observe(this, Observer {
            binding.rvEvents.adapter?.notifyDataSetChanged()
        })

        viewModel.onError.observe(this, Observer {
            showAlert(message = it ?: R.string.default_body_error)
        })

    }
}