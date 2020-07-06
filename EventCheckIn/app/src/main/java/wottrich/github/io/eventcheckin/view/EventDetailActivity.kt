package wottrich.github.io.eventcheckin.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.archive.openMap
import wottrich.github.io.eventcheckin.archive.shareText
import wottrich.github.io.eventcheckin.archive.showAlert
import wottrich.github.io.eventcheckin.databinding.ActivityEventDetailBinding
import wottrich.github.io.eventcheckin.view.dialog.CheckInDialog
import wottrich.github.io.eventcheckin.viewModel.EventDetailViewModel

class EventDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: EventDetailViewModel
    private lateinit var binding: ActivityEventDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
        viewModel = ViewModelProvider(this)[EventDetailViewModel::class.java]

        binding.apply {
            lifecycleOwner = this@EventDetailActivity
            event = viewModel.event
            isLoading = viewModel.isLoading
        }

        setupListeners()
        setupObservers()

        viewModel.loadEventDetails(intent)

    }

    private fun setupListeners() {

        binding.toolbar.apply {

            setNavigationOnClickListener {
                onBackPressed()
            }

            setOnMenuItemClickListener {
                if (it.itemId == R.id.itShare) {
                    shareEvent()
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
        }

        binding.btnSeeLocation.setOnClickListener {
            openMap()
        }

        binding.btnCheckIn.setOnClickListener {
            CheckInDialog.show(
                supportFragmentManager,
                viewModel::confirmCheckIn,
                viewModel::setError
            )
        }

    }

    private fun setupObservers() {

        viewModel.event.observe(this, Observer {
            if (it != null) {
                Glide.with(this)
                    .load(it.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(binding.ivEvent)
            }
        })

        viewModel.onError.observe(this, Observer {
            showAlert(
                title = R.string.default_title_error,
                message = it ?: R.string.default_body_error,
                callback = this::finish
            )
        })

        viewModel.successCheckIn.observe(this, Observer {
            val success = it ?: false
            if (success) {
                showAlert(
                    title = R.string.event_detail_success_check_in_message_title,
                    message = R.string.event_detail_success_check_in_message_body,
                    callback = this::finish
                )
            } else {
                showAlert(message = R.string.check_in_error_message)
            }
        })

    }

    private fun shareEvent() {
        val textToShare = viewModel.event.value?.toString() ?: ""
        val intent = Intent().shareText(textToShare)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun openMap() {
        val lat = viewModel.event.value?.latitude ?: 0.0
        val lon = viewModel.event.value?.longitude ?: 0.0

        val intent = Intent().openMap(lat, lon)

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}