package com.janwelcris.mvvp.base.navigation

import android.os.Bundle

sealed class NavigationCommand {
    data class ToDirection(val resId: Int, val bundle: Bundle? = null) : NavigationCommand()
    object Back : NavigationCommand()
}