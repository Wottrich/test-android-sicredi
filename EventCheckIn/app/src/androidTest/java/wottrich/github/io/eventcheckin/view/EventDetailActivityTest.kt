package wottrich.github.io.eventcheckin.view

//import org.hamcrest.MatcherAssert.assertThat
//import org.junit.Assert.assertThat

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.widget.AppCompatImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import wottrich.github.io.eventcheckin.R


/**
 * @author Wottrich
 * @author wottrich78@gmail.com
 * @since 07/07/20
 *
 *
 * Copyright © 2020 EventCheckIn. All rights reserved.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
open class EventDetailActivityTest {

//    @get:Rule
//    var activityScenarioRule: ActivityScenarioRule<EventDetailActivity>
//            = ActivityScenarioRule(EventDetailActivity::class.java)

    @get:Rule
    var mActivityRule: IntentsTestRule<EventDetailActivity> = IntentsTestRule(EventDetailActivity::class.java)

    @Before
    fun setUp() {
        intending(not(isInternal()))
            .respondWith(ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun givenAEventDetail_WhenButtonCheckInRequested_ThenShouldOpenConfirmModal () {

        onView(withId(R.id.btnCheckIn))
            .perform(click())

    }

    @Test
    fun givenAEventDetail_WhenButtonLocationRequested_ThenShouldOpenMapIntent () {

        onView(withId(R.id.btnSeeLocation))
            .perform(click())

        val intent = Iterables.getOnlyElement(Intents.getIntents())
        assertThat(intent.action, equalTo(Intent.ACTION_VIEW))

    }

    @Test
    fun givenAEventDetail_WhenButtonShareRequested_ThenShouldOpenShareOptions () {

        onView(withId(R.id.itShare))
            .perform(click())
            .check(matches(isDisplayed()))

        val intent = Iterables.getOnlyElement(Intents.getIntents())
        assertThat(intent.action, equalTo(Intent.ACTION_SEND))
        assertThat(intent.type, equalTo("text/plain"))

    }

    @Test
    fun givenAEventDetail_WhenButtonBackRequested_ThenShouldCloseDetail () {

        onView(allOf(
            instanceOf(AppCompatImageButton::class.java), withParent(withId(R.id.toolbar))
        )).perform(click()).noActivity()

    }

}