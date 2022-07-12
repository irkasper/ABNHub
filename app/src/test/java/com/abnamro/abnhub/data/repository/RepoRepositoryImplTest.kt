package com.abnamro.abnhub.data.repository

import com.abnamro.abnhub.data.local_data_source.RepoDao
import com.abnamro.abnhub.data.model.RepoDto
import com.abnamro.abnhub.data.model.RepoModel
import com.abnamro.abnhub.data.remote_data_source.GitHubService
import com.abnamro.abnhub.data.util.RepoDetailDomainMapper
import com.abnamro.abnhub.data.util.RepoDomainMapper
import com.abnamro.abnhub.data.util.RepoModelMapper
import com.abnamro.abnhub.domain.entity.Repo
import com.abnamro.abnhub.domain.entity.RepoDetail
import com.abnamro.abnhub.domain.util.Mapper
import com.abnamro.abnhub.test_util.BaseRobot
import com.abnamro.abnhub.test_util.TestCoroutineRule
import com.abnamro.abnhub.test_util.TestUtil
import com.abnamro.abnhub.utils.PAGE_SIZE
import com.abnamro.abnhub.utils.ResourcesProvider
import com.abnamro.abnhub.utils.Result
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class RepoRepositoryImplTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val robot = Robot()

    @Before
    fun setUp() {
        robot.setUp()
    }

//    @Test
//    fun observe() = runTest {
//        with(robot) {
//            // arrange
//
//
//            // act
//            val r = systemUnderTest.observeRepos().toList()
//
//            // assert
//            assertThat(r)
//            verify_repoDaoMock_observeRepos()
//            verify_repoDomainMapperMock_mapList()
//        }
//    }

    @Test
    fun updateData_notFirstPage_onApiSuccess() {
        val page = Random.nextInt()
        updateData_onApiSuccess_common(page)
    }

    @Test
    fun updateData_firstPage_onApiSuccess() {
        val page = 1
        updateData_onApiSuccess_common(page)
    }

    private fun updateData_onApiSuccess_common(page: Int) =
        testCoroutineRule.runTestWithTestDispatcher {
            with(robot) {
                // arrange
                arrange_gitHubServiceMock_fetchRepos_success()

                // act
                val r = systemUnderTest.updateData(page)

                // assert
                assertThat(r).isEqualTo(Result.Success(TestUtil.tRepoDtoList.size))
                assertCall_gitHubServiceMock_fetchRepos(page)
                assertCall_repoDaoMock_deleteAll(page == 1)
                assertCall_repoDaoMock_insertRepos(true)
            }
        }

    @Test
    fun updateData_onApiError() = testCoroutineRule.runTestWithTestDispatcher {
        with(robot) {
            // arrange
            arrange_gitHubServiceMock_fetchRepos_error()

            // act
            val r = systemUnderTest.updateData(1)

            // assert
            assertThat(r).isInstanceOf(Result.Error::class.java)
            assertCall_gitHubServiceMock_fetchRepos(null)
            assertCall_repoDaoMock_deleteAll(false)
            assertCall_repoDaoMock_insertRepos(false)
        }
    }

    @Test
    fun getRepoDetail_onApiSuccess() = testCoroutineRule.runTestWithTestDispatcher {
        with(robot) {
            // arrange
            arrange_gitHubServiceMock_getRepoDetail_success()

            // act
            val r = systemUnderTest.getRepoDetail("")

            // assert
            assertThat(r).isInstanceOf(Result.Success::class.java)
            assertCall_gitHubServiceMock_getRepoDetail()
        }
    }

    @Test
    fun getRepoDetail_onApiError_onCacheSuccess() = testCoroutineRule.runTestWithTestDispatcher {
        with(robot) {
            // arrange
            arrange_gitHubServiceMock_getRepoDetail_error()
            arrange_repoDaoMock_getRepo_success()

            // act
            val r = systemUnderTest.getRepoDetail("")

            // assert
            assertThat(r).isInstanceOf(Result.Success::class.java)
            assertCall_gitHubServiceMock_getRepoDetail()
        }
    }

    @Test
    fun getRepoDetail_onApiError_onNoCache() = testCoroutineRule.runTestWithTestDispatcher {
        with(robot) {
            // arrange
            arrange_gitHubServiceMock_getRepoDetail_error()
            arrange_repoDaoMock_getRepo_nullReturn()

            // act
            val r = systemUnderTest.getRepoDetail("")

            // assert
            assertThat(r).isInstanceOf(Result.Error::class.java)
            assertCall_gitHubServiceMock_getRepoDetail()
        }
    }

    private class Robot : BaseRobot() {

        private val resourcesProviderMock = mockk<ResourcesProvider>(relaxed = true)
        private val gitHubServiceMock = mockk<GitHubService>()
        private val repoDaoMock = mockk<RepoDao>(relaxed = true)
        private val repoDomainMapperMock = spyk<Mapper<RepoModel, Repo>>(RepoDomainMapper())
        private val repoDetailDomainMapperMock =
            spyk<Mapper<RepoModel, RepoDetail>>(RepoDetailDomainMapper())
        private val repoModelMapperMock = spyk<Mapper<RepoDto, RepoModel>>(RepoModelMapper())


        lateinit var systemUnderTest: RepoRepositoryImpl

        override fun setUp() {
            systemUnderTest = RepoRepositoryImpl(
                resourcesProvider = resourcesProviderMock,
                gitHubService = gitHubServiceMock,
                repoDao = repoDaoMock,
                repoDomainMapper = repoDomainMapperMock,
                repoDetailDomainMapper = repoDetailDomainMapperMock,
                repoModelMapper = repoModelMapperMock,
            )
        }

        fun arrange_gitHubServiceMock_fetchRepos_success() {
            coEvery { gitHubServiceMock.fetchRepos(any(), any()) } returns TestUtil.tRepoDtoList
        }

        fun arrange_gitHubServiceMock_fetchRepos_error() {
            coEvery { gitHubServiceMock.fetchRepos(any(), any()) } throws UnknownHostException()
        }

        fun arrange_gitHubServiceMock_getRepoDetail_success() {
            coEvery { gitHubServiceMock.getRepoDetail(any()) } returns TestUtil.tRepoDto1
        }

        fun arrange_gitHubServiceMock_getRepoDetail_error() {
            coEvery { gitHubServiceMock.getRepoDetail(any()) } throws UnknownHostException()
        }

        fun arrange_repoDaoMock_getRepo_success() {
            coEvery { repoDaoMock.getRepo(any()) } returns TestUtil.tRepoModel
        }

        fun arrange_repoDaoMock_getRepo_nullReturn() {
            coEvery { repoDaoMock.getRepo(any()) } returns null
        }

        fun assertCall_gitHubServiceMock_fetchRepos(page: Int?) {
            coVerify(exactly = 1) { gitHubServiceMock.fetchRepos(page ?: any(), PAGE_SIZE) }
        }

        fun assertCall_gitHubServiceMock_getRepoDetail() {
            coVerify(exactly = 1) { gitHubServiceMock.getRepoDetail(any()) }
        }

        fun assertCall_repoDaoMock_deleteAll(shouldCall: Boolean) {
            coVerify(exactly = if (shouldCall) 1 else 0) { repoDaoMock.deleteAll() }
        }

        fun assertCall_repoDaoMock_insertRepos(shouldCall: Boolean) {
            coVerify(exactly = if (shouldCall) 1 else 0) { repoDaoMock.insertRepos(any()) }
        }

//        fun arrange_repoDaoMock_observeRepos() {
//            every { repoDaoMock.observeRepos() } returns flowOf(
//
//            )
//        }
//
//        fun verify_repoDaoMock_observeRepos() {
//            verify(exactly = 1) { repoDaoMock.observeRepos() }
//        }
//
//        fun verify_repoDomainMapperMock_mapList() {
//            verify(exactly = 1) { repoDomainMapperMock.mapList(any()) }
//        }
    }
}