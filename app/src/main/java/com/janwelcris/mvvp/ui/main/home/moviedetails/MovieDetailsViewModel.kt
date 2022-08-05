package com.janwelcris.mvvp.ui.main.home.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.janwelcris.mvvp.base.BaseFragmentViewModel
import com.janwelcris.mvvp.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel  @Inject constructor() : BaseFragmentViewModel() {

    private var _movieModel = MutableLiveData<MovieModel>()
    var movieLiveData: LiveData<MovieModel> = _movieModel

    fun initMovieDetails(photo: MovieModel) {
        _movieModel.value = photo
    }
}
