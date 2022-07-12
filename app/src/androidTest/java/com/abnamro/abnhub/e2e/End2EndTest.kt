package com.abnamro.abnhub.e2e

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.abnamro.abnhub.R
import com.abnamro.abnhub.test_util.BaseRobot
import com.abnamro.abnhub.test_util.RUN_UI_TEST
import com.abnamro.abnhub.test_util.THEN
import com.abnamro.abnhub.test_util.WHEN
import com.abnamro.abnhub.ui.main.MainActivity
import com.abnamro.abnhub.ui.screens.repo_list.RepoAdapter
import com.abnamro.abnhub.ui.util.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class End2EndTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = createAndroidComposeRule(MainActivity::class.java)

    private val robot = Robot(activityRule)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
        robot.setUp()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
        robot.tearsDown()
    }

    @Test
    fun checkAllListItemsOnScrolling() {
        RUN_UI_TEST(robot) {
            WHEN { clickOnFirstListItem() }
            THEN { assert_triggerBackBtn() }
//            THEN { checkToolbarText() }
        }
    }

    private class Robot(private val activityRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) :
        BaseRobot() {

        private val context = ApplicationProvider.getApplicationContext<Context>()


        fun clickOnFirstListItem() {
            onView(ViewMatchers.withId(R.id.repo_recyclerview))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RepoAdapter.ViewHolder>(
                        0,
                        ViewActions.click()
                    )
                )
            Thread.sleep(2000)
        }

        fun assert_triggerBackBtn() {
            activityRule.onNodeWithContentDescription(context.getString(R.string.cd_back_to_home_screen))
                .assertIsDisplayed()
        }

        fun navigateBack() {
            Espresso.pressBack()
        }

        fun checkToolbarText() {
            onView(withText(context.getString(R.string.app_name))).check(matches(isDisplayed()))
        }
    }
}