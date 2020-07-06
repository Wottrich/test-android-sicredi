package wottrich.github.io.eventcheckin.viewModel

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.dialog_check_in.view.*

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 05/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
class CheckInDialogViewModel : ViewModel() {

    private val mEmail: MutableLiveData<String> = MutableLiveData()
    val email: String
        get() = mEmail.value ?: ""

    private val mName: MutableLiveData<String> = MutableLiveData()
    val name: String
        get() = mName.value ?: ""

    private val mFormIsValid: MutableLiveData<Boolean> = MutableLiveData()

    val formIsValidLiveData: LiveData<Boolean>
        get() = mFormIsValid

    val formIsValid: Boolean
        get() = formIsValidLiveData.value ?: false

    fun watchers (nameEditText: EditText, emailEditText: EditText) {

        nameEditText.doAfterTextChanged {
            mName.value = it.toString()
            validForm()
        }

        emailEditText.doAfterTextChanged {
            mEmail.value = it.toString()
            validForm()
        }

    }

    private fun validForm () {
        mFormIsValid.value = name.isNotEmpty() && email.isNotEmpty()
    }

}