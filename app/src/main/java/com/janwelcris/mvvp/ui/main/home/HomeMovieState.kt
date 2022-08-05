package com.janwelcris.mvvp.ui.main.home

sealed class HomeMovieState
object LoadingMovieState : HomeMovieState()

object ContentMovieState : HomeMovieState()

object LoadingMovieNextPageState : HomeMovieState()

object EmptyMovieState : HomeMovieState()
class ErrorMovieState(val message: String) : HomeMovieState()

