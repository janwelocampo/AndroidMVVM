package com.janwelcris.mvvp.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.janwelcris.mvvp.base.navigation.Event
import com.janwelcris.mvvp.base.navigation.NavigationCommand

abstract class BaseFragmentViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    fun navigate(resId: Int, bundle: Bundle? = null) {
        _navigation.value = Event(NavigationCommand.ToDirection(resId, bundle))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

}