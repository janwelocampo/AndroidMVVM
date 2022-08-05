package com.janwelcris.mvvp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janwelcris.mvvp.data.DataManager
import com.janwelcris.mvvp.data.DataState
import com.janwelcris.mvvp.data.usecase.authentication.GetSessionIDUseCase
import com.janwelcris.mvvp.data.usecase.authentication.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getSessionIDUseCase: GetSessionIDUseCase,
    private val dm:DataManager
) : ViewModel() {

    private var _uiState = MutableLiveData<SplashUiState>()
    var uiStateLiveData: LiveData<SplashUiState> = _uiState


    fun initSplashScreen() {
        _uiState.postValue(LoadingState)
        getUserToken()
    }

    fun getUserToken(){
        viewModelScope.launch {
            getUserTokenUseCase().collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val response = dataState.data
                        dm.userToken = response.request_token

                        getSessionID()
                    }

                    is DataState.Error -> {

                    }
                }
            }
        }
    }

    fun getSessionID(){
        viewModelScope.launch {
            getSessionIDUseCase().collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val response = dataState.data
                        dm.sessionID = response.guest_session_id
                        _uiState.postValue(ContentState)
                    }

                    is DataState.Error -> {
                        getUserTokenUseCase
                    }
                }
            }
        }
    }

}
