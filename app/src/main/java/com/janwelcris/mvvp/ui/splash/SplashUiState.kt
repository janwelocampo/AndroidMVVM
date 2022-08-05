package com.janwelcris.mvvp.ui.splash

sealed class SplashUiState
object LoadingState : SplashUiState()
object ContentState : SplashUiState()