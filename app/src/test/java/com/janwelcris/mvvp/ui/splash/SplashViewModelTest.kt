package com.janwelcris.mvvp.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.janwelcris.mvvp.data.DataManager
import com.janwelcris.mvvp.data.DataState
import com.janwelcris.mvvp.data.repository.authentication.AuthenticationRepository
import com.janwelcris.mvvp.data.usecase.authentication.GetSessionIDUseCase
import com.janwelcris.mvvp.data.usecase.authentication.GetUserTokenUseCase
import com.janwelcris.mvvp.model.response.SessionIDResponse
import com.janwelcris.mvvp.model.response.UserTokenResponse
import com.janwelcris.mvvp.utils.TestCoroutineRule
import com.janwelcris.mvvp.utils.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: SplashViewModel

    @Mock
    private lateinit var dm: DataManager

    private lateinit var getSessionIDUseCase: GetSessionIDUseCase

    @Mock
    private lateinit var getUserTokenUseCase: GetUserTokenUseCase

    @Mock
    private lateinit var authenticationRepository: AuthenticationRepository


    @Mock
    private lateinit var apiSessionIDObserver: Observer<SplashUiState>

    @Before
    fun setUp() {
        // do something if required
        getSessionIDUseCase = Mockito.spy(GetSessionIDUseCase(authenticationRepository))
        getUserTokenUseCase = Mockito.spy(GetUserTokenUseCase(authenticationRepository))
        viewModel = SplashViewModel(getUserTokenUseCase, getSessionIDUseCase, dm)
    }

    private fun fetchSessionIDSuccess() = flow<DataState<SessionIDResponse>> {
        emit(DataState.success(TestData.sessionIDResponse))
    }

    private fun fetchSessionIDFailed() = flow<DataState<SessionIDResponse>> {
        emit(DataState.error(TestData.errorNetworkString))
    }

    private fun fetchTokenSuccess() = flow<DataState<UserTokenResponse>> {
        emit(DataState.success(TestData.tokenResponse))
    }

    private fun fetchTokenFailed() = flow<DataState<UserTokenResponse>> {
        emit(DataState.error(TestData.errorNetworkString))
    }

    @Test
    fun givenSplashResponse_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(fetchTokenSuccess())
                .`when`(getUserTokenUseCase).invoke()
            doReturn(fetchSessionIDSuccess())
                .`when`(getSessionIDUseCase).invoke()

            viewModel.initSplashScreen()

            viewModel.uiStateLiveData.observeForever(apiSessionIDObserver)
            verify(getUserTokenUseCase).invoke()
            verify(getSessionIDUseCase).invoke()
            verify(apiSessionIDObserver).onChanged(ContentState)
            viewModel.uiStateLiveData.removeObserver(apiSessionIDObserver)
        }
    }

    @Test
    fun givenTokenResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(fetchTokenSuccess())
                .`when`(getUserTokenUseCase).invoke()
            doReturn(fetchSessionIDSuccess())
                .`when`(getSessionIDUseCase).invoke()

            viewModel.getUserToken()

            viewModel.uiStateLiveData.observeForever(apiSessionIDObserver)
            verify(getUserTokenUseCase).invoke()
            verify(getSessionIDUseCase).invoke()
            verify(apiSessionIDObserver).onChanged(ContentState)
            viewModel.uiStateLiveData.removeObserver(apiSessionIDObserver)
        }
    }

    @Test
    fun givenTokenResponse400_whenFetch_shouldReturnFailed() {
        testCoroutineRule.runBlockingTest {
            doReturn(fetchTokenFailed())
                .`when`(getUserTokenUseCase).invoke()

            viewModel.getUserToken()

            viewModel.uiStateLiveData.observeForever(apiSessionIDObserver)
            verify(getUserTokenUseCase).invoke()
            viewModel.uiStateLiveData.removeObserver(apiSessionIDObserver)
        }
    }

    @Test
    fun givenSessionIDResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(fetchSessionIDSuccess())
                .`when`(getSessionIDUseCase).invoke()

            viewModel.getSessionID()

            viewModel.uiStateLiveData.observeForever(apiSessionIDObserver)
            verify(getSessionIDUseCase).invoke()
            verify(apiSessionIDObserver).onChanged(ContentState)
            viewModel.uiStateLiveData.removeObserver(apiSessionIDObserver)
        }
    }


    @Test
    fun givenSessionIDResponse400_whenFetch_shouldReturnFailed() {
        testCoroutineRule.runBlockingTest {
            doReturn(fetchSessionIDFailed())
                .`when`(getSessionIDUseCase).invoke()
            viewModel.getSessionID()

            viewModel.uiStateLiveData.observeForever(apiSessionIDObserver)
            verify(getSessionIDUseCase).invoke()
            viewModel.uiStateLiveData.removeObserver(apiSessionIDObserver)
        }
    }
}