package wottrich.github.io.eventcheckin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_events_list)
        viewModel = EventsListViewModel()

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
                        activity.viewModel.onItemClick(activity, eventId)
                    }
                }
            }
        }
    }

    private fun setupObservers () {

        viewModel.activityToGo.observe(this, Observer {
            if (it != null) {
                startActivity(it)
            }
        })

        viewModel.events.observe(this, Observer {
            binding.rvEvents.adapter?.notifyDataSetChanged()
        })

        viewModel.onError.observe(this, Observer {
            showAlert(getString(R.string.default_title_error), getString(it))
        })

    }
}