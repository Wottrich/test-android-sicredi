package wottrich.github.io.eventcheckin.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
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

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            onConfirmCheckIn: (name: String, email: String) -> Unit
        ) {
            val newFragment = CheckInDialog()
            newFragment.onConfirmCheckIn = onConfirmCheckIn
            newFragment.show(fragmentManager, "CheckInDialog")
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

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes?.let {
            it.width = ViewGroup.LayoutParams.MATCH_PARENT
            it.height = ViewGroup.LayoutParams.MATCH_PARENT
            it.dimAmount = 0f
        }
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
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
                Toast.makeText(requireActivity(), getString(R.string.check_in_dialog_complete_form), Toast.LENGTH_SHORT).show()
            }
            dismissDialog()
        }

        this.root.ibClose.setOnClickListener {
            dismissDialog()
        }
    }

    private fun dismissDialog() {
        dismissAllowingStateLoss()
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun setupWatchers () {

        root.etName.doAfterTextChanged {
            viewModel.setName(it.toString())
        }

        root.etEmail.doAfterTextChanged {
            viewModel.setEmail(it.toString())
        }

    }

    private fun setupObservers () {

        viewModel.formIsValidLiveData.observe(viewLifecycleOwner, Observer {
            this.root.btnConfirmCheckIn.isEnabled = it ?: false
        })

    }

}