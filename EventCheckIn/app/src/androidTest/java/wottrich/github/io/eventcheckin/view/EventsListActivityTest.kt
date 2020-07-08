package wottrich.github.io.eventcheckin.view

import android.app.Activity
import android.app.Instrumentation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import wottrich.github.io.eventcheckin.R
import wottrich.github.io.eventcheckin.model.Event
import wottrich.github.io.eventcheckin.view.adapter.EventsViewHolder
import java.lang.Thread.sleep

/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 08/07/20
 *
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class EventsListActivityTest {

    @get:Rule
    var mActivityRule: IntentsTestRule<EventsListActivity> =
        IntentsTestRule(EventsListActivity::class.java)

    @Before
    fun setUp() {
        intending(not(isInternal()))
            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun givenAEventList_WhenItemClickRequested_ThenShouldOpenEventDetailActivity() {
        sleep(2000)

        onView(withId(R.id.rvEvents))
            .perform(RecyclerViewActions.actionOnItemAtPosition<EventsViewHolder>(0, click()))
            .noActivity()

    }

}