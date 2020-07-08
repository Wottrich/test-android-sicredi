package wottrich.github.io.eventcheckin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 04/07/20
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 *
 */
 
abstract class BaseViewModel(
    final override val coroutineContext: CoroutineContext
): ViewModel(), CoroutineScope {

    protected val scope = CoroutineScope(coroutineContext)

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onError: MutableLiveData<Int> = MutableLiveData()

}