package com.abnamro.abnhub.ui.screens.repo_detail

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.abnamro.abnhub.R
import com.abnamro.abnhub.domain.entity.RepoDetail
import com.abnamro.abnhub.test_util.BaseRobot
import com.abnamro.abnhub.test_util.GIVEN
import com.abnamro.abnhub.test_util.RUN_UI_TEST
import com.abnamro.abnhub.test_util.THEN
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepoDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val robot = Robot(composeTestRule)

    @Before
    fun setUp() {
        robot.setUp()
    }

    @After
    fun tearDown() {
        robot.tearsDown()
    }

    @Test
    fun testScreenItemsVisibility_onSuccessRepoDetail() {
        RUN_UI_TEST(robot) {
            GIVEN { given_successRepoDetail() }
            THEN { checkToolbarTextDisplay(uiState.value.repo!!.name) }
            THEN { checkRepoFullNameDisplay() }
            THEN { checkRepoDescriptionDisplay() }
            THEN { checkRepoVisibilityDisplay() }
            THEN { checkRepoPrivateDisplay() }
        }
    }

    @Test
    fun testLoadingState() {
        RUN_UI_TEST(robot) {
            GIVEN { given_setLoadingTrue() }
            THEN { checkToolbarTextDisplay(R.string.loading) }
        }
    }

    @Test
    fun testErrorState() {
        RUN_UI_TEST(robot) {
            GIVEN { given_setError() }
            THEN { checkToolbarTextDisplay(R.string.error) }
        }
    }

    @Test
    fun testNavigateUpOnBackBtn() {
        RUN_UI_TEST(robot) {
            GIVEN { given_triggerBackBtn() }
            THEN { checkBackBtnTriggered() }
        }
    }

    private class Robot(private val composeTestRule: ComposeContentTestRule) : BaseRobot() {

        val tRepoDetail = RepoDetail(
            name = "repo1",
            fullName = "repo1-full-name",
            description = "repo1 desc",
            avatarUrl = "https://avatars.githubusercontent.com/u/15876397?v=4",
            visibility = "public",
            isPrivate = false,
        )

        private val context = ApplicationProvider.getApplicationContext<Context>()

        lateinit var uiState: MutableState<RepoDetailUiState>

        private var navigateUpIndicator: Boolean? = null

        override fun setUp() {
            uiState = mutableStateOf(RepoDetailUiState())
            navigateUpIndicator = null
            composeTestRule.setContent {
                RepoDetailScreen(uiState = uiState.value) {
                    navigateUpIndicator = true
                }
            }
        }

        fun given_successRepoDetail() {
            uiState.value = RepoDetailUiState(repo = tRepoDetail)
        }

        fun given_setLoadingTrue() {
            uiState.value = RepoDetailUiState(isLoading = true)
        }

        fun given_setError() {
            uiState.value = RepoDetailUiState(errorMessage = "")
        }

        fun given_triggerBackBtn() {
            composeTestRule.onNodeWithContentDescription(context.getString(R.string.cd_back_to_home_screen))
                .performClick()
        }

        fun checkToolbarTextDisplay(text: String) {
            composeTestRule.onNodeWithText(text).assertIsDisplayed()
        }

        fun checkToolbarTextDisplay(@StringRes text: Int) {
            composeTestRule.onNodeWithText(context.getString(text)).assertIsDisplayed()
        }

        fun checkRepoFullNameDisplay() {
            composeTestRule.onNodeWithText(
                context.getString(
                    R.string.screen_repodetail_fullname,
                    uiState.value.repo!!.fullName
                )
            ).assertIsDisplayed()
        }

        fun checkRepoDescriptionDisplay() {
            composeTestRule.onNodeWithText(
                context.getString(
                    R.string.screen_repodetail_description,
                    uiState.value.repo!!.description
                )
            ).assertIsDisplayed()
        }

        fun checkRepoVisibilityDisplay() {
            composeTestRule.onNodeWithText(
                context.getString(
                    R.string.screen_repodetail_visibility,
                    uiState.value.repo!!.visibility
                )
            ).assertIsDisplayed()
        }

        fun checkRepoPrivateDisplay() {
            composeTestRule.onNodeWithText(
                context.getString(
                    R.string.screen_repodetail_private,
                    uiState.value.repo!!.isPrivate
                )
            ).assertIsDisplayed()
        }

        fun checkBackBtnTriggered() {
            assertThat(navigateUpIndicator).isTrue()
        }

    }
}