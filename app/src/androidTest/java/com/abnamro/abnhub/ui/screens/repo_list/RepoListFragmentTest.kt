package com.abnamro.abnhub.ui.screens.repo_list

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.abnamro.abnhub.R
import com.abnamro.abnhub.test_util.BaseRobot
import com.abnamro.abnhub.test_util.RUN_UI_TEST
import com.abnamro.abnhub.test_util.THEN
import com.abnamro.abnhub.test_util.WHEN
import com.abnamro.abnhub.test_util.launchFragmentInHiltContainer
import com.abnamro.abnhub.ui.main.HiltTestActivity
import com.abnamro.abnhub.ui.util.EspressoIdlingResource
import com.google.common.truth.Truth.assertThat
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
class RepoListFragmentTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    private val robot = Robot()

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
            WHEN { launchRepoListFragment() }
            THEN { checkToolbarText() }
            THEN { checkAllListItemsOnScrolling() }
            THEN { checkSnackbarText_onNoMoreData() }
        }
    }

    private class Robot : BaseRobot() {

        private val context = ApplicationProvider.getApplicationContext<Context>()

        lateinit var frag: RepoListFragment

        lateinit var activityScenario: ActivityScenario<HiltTestActivity>

        override fun setUp() {
        }

        override fun tearsDown() {
            activityScenario.close()
        }

        fun launchRepoListFragment() {
            activityScenario = launchFragmentInHiltContainer<RepoListFragment> {
                frag = this as RepoListFragment
            }
        }

        fun checkToolbarText() {
            onView(withText(context.getString(R.string.app_name))).check(matches(isDisplayed()))
        }

        fun checkAllListItemsOnScrolling() {
            while (frag.viewModel.nextPage > 0) {
                val lstRepos = frag.viewModel.uiState.value.lstRepos
                assertThat(lstRepos).isNotEmpty()

                for ((index, repo) in lstRepos.withIndex()) {
                    onView(ViewMatchers.withId(R.id.repo_recyclerview))
                        .perform(
                            RecyclerViewActions.scrollToPosition<RepoAdapter.ViewHolder>(index)
                        )
                    onView(withText(repo.name)).check(matches(isDisplayed()))
                }
            }
        }

        fun checkSnackbarText_onNoMoreData() {
            onView(withText(context.getString(R.string.no_more_data))).check(matches(isDisplayed()))
        }
    }
}