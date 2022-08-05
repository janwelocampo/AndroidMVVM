package com.janwelcris.mvvp.ui.main.home

sealed class HomeTagState
object LoadingTagState : HomeTagState()
object ContentTagState : HomeTagState()

