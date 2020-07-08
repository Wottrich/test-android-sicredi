package wottrich.github.io.eventcheckin.viewModel

import android.os.Build
import android.widget.EditText
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.Matchers.*
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 06/07/20
 *
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.P])
class CheckInDialogViewModelTest {

    private lateinit var sut: CheckInDialogViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = CheckInDialogViewModel()
    }

    @Test
    fun `given name and email when text watcher is requested then should return formIsValid true` () {

        assertThat(sut.formIsValid, equalTo(false))
        assertThat(sut.name, isEmptyString())
        assertThat(sut.email, isEmptyString())

        //given
        val name = "Lucas"
        val email = "lucas@gmail.com"

        //when
        sut.setName(name)
        sut.setEmail(email)

        //then
        assertThat(sut.formIsValid, equalTo(true))
        assertThat(sut.name, equalTo(name))
        assertThat(sut.email, equalTo(email))

    }
}