package wottrich.github.io.eventcheckin.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.dialog_check_in.view.*
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.viewModel.CheckInDialogViewModel

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 05/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
class CheckInDialog : DialogFragment() {

    private lateinit var viewModel: CheckInDialogViewModel
    private lateinit var root: View

    var onConfirmCheckIn: ((name: String, email: String) -> Unit)? = null
    var onError: ((errorMessage: Int) -> Unit)? = null

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            onConfirmCheckIn: (name: String, email: String) -> Unit,
            onError: (errorMessage: Int) -> Unit
        ) {

            val newFragment = CheckInDialog()
            newFragment.onConfirmCheckIn = onConfirmCheckIn
            newFragment.onError = onError

            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null)
                .commit()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_check_in, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        this.root = view
        this.viewModel = ViewModelProvider(this)[CheckInDialogViewModel::class.java]

        setupWatchers()
        setupObservers()
        setupListeners()
    }

    private fun setupListeners () {
        this.root.btnConfirmCheckIn.setOnClickListener {
            if (viewModel.formIsValid && onConfirmCheckIn != null) {
                onConfirmCheckIn?.invoke(viewModel.name, viewModel.email)
            } else {
                onError?.invoke(R.string.check_in_error_message)
            }
            dismiss()
        }
    }

    private fun setupWatchers () {
        viewModel.watchers(root.etName, root.etEmail)
    }

    private fun setupObservers () {

        viewModel.formIsValidLiveData.observe(viewLifecycleOwner, Observer {
            this.root.btnConfirmCheckIn.isEnabled = it ?: false
        })

    }

}