package com.janwelcris.mvvp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.janwelcris.mvvp.base.BaseFragmentViewModel
import com.janwelcris.mvvp.data.DataState
import com.janwelcris.mvvp.data.usecase.movies.GetNowPlayingMoviesUseCase
import com.janwelcris.mvvp.data.usecase.movies.GetPopularMoviesUseCase
import com.janwelcris.mvvp.data.usecase.movies.GetTopRatedMoviesUseCase
import com.janwelcris.mvvp.data.usecase.movies.GetUpcomingMoviesUseCase
import com.janwelcris.mvvp.model.MovieModel
import com.janwelcris.mvvp.model.TagModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val getNowPlayingUseCase: GetNowPlayingMoviesUseCase,
    private val  getPopularUseCase: GetPopularMoviesUseCase,
    private val  getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private  val  getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : BaseFragmentViewModel() {

    private var _homeMovieState = MutableLiveData<HomeMovieState>()
    var movieListState: LiveData<HomeMovieState> = _homeMovieState

    private var _homeTagState = MutableLiveData<HomeTagState>()
    var tagListState: LiveData<HomeTagState> = _homeTagState

    private val _tagList by lazy {
        return@lazy MutableLiveData<List<TagModel>>()
    }

    var tagListLiveData: LiveData<List<TagModel>> = _tagList

    private val _movieList by lazy {
        return@lazy MutableLiveData<ArrayList<MovieModel>>()
    }
    var movieListLiveData: LiveData<ArrayList<MovieModel>> = _movieList


    private var pageNumber = 1
    private var movieTag = "Now Playing"

    init {
      initTagsMovies()
        initMovieList()
    }

    private fun initMovieList(){
        pageNumber = 1
        getNowPlayingMovieList()
    }

    private fun initTagsMovies(){
        //No API based on MovieDB just add static values
        _homeTagState.postValue(LoadingTagState)
        val tagList = arrayListOf(
            TagModel(
                tagName = "Now Playing",
            ),
            TagModel(
                tagName = "Popular",
            ),
            TagModel(
                tagName = "Top Rated",
            ),
            TagModel(
                tagName = "Upcoming",
            )
        )
        _tagList.postValue(tagList)
        _homeTagState.postValue(ContentTagState)
    }

    fun loadMore(){
        pageNumber++
        getMoviesByTag()
    }

    fun searchByTag(tag:String){
        this.movieTag = tag
        pageNumber = 1
        getMoviesByTag()
    }

    private fun getMoviesByTag(){
        when(movieTag){
            "Now Playing" -> getNowPlayingMovieList()
            "Popular" -> getPopularMovieList()
            "Top Rated" -> getTopRatedMovieList()
            "Upcoming" -> getUpcomingMovieList()

        }
    }

    private fun getNowPlayingMovieList(){
        _homeMovieState.postValue(
            if (pageNumber == 1)
                LoadingMovieState
            else
                LoadingMovieNextPageState
        )
        viewModelScope.launch {
            getNowPlayingUseCase(pageNumber).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _homeMovieState.postValue(ContentMovieState)
                        _movieList.postValue(dataState.data.results)
                    }

                    is DataState.Error -> {
                        _movieList.postValue(arrayListOf())
                        _homeMovieState.postValue(ErrorMovieState(dataState.message))
                    }
                }
            }
        }
    }

    private fun getPopularMovieList(){
        _homeMovieState.postValue(
            if (pageNumber == 1)
                LoadingMovieState
            else
                LoadingMovieNextPageState
        )
        viewModelScope.launch {
            getPopularUseCase(pageNumber).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _homeMovieState.postValue(ContentMovieState)
                        _movieList.postValue(dataState.data.results)
                    }

                    is DataState.Error -> {
                        _movieList.postValue(arrayListOf())
                        _homeMovieState.postValue(ErrorMovieState(dataState.message))
                    }
                }
            }
        }
    }

    private fun getTopRatedMovieList(){
        _homeMovieState.postValue(
            if (pageNumber == 1)
                LoadingMovieState
            else
                LoadingMovieNextPageState
        )
        viewModelScope.launch {
            getTopRatedMoviesUseCase(pageNumber).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _homeMovieState.postValue(ContentMovieState)
                        _movieList.postValue(dataState.data.results)
                    }

                    is DataState.Error -> {
                        _movieList.postValue(arrayListOf())
                        _homeMovieState.postValue(ErrorMovieState(dataState.message))
                    }
                }
            }
        }
    }

    private fun getUpcomingMovieList(){
        _homeMovieState.postValue(
            if (pageNumber == 1)
                LoadingMovieState
            else
                LoadingMovieNextPageState
        )
        viewModelScope.launch {
            getUpcomingMoviesUseCase(pageNumber).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        _homeMovieState.postValue(ContentMovieState)
                        _movieList.postValue(dataState.data.results)
                    }

                    is DataState.Error -> {
                        _movieList.postValue(arrayListOf())
                        _homeMovieState.postValue(ErrorMovieState(dataState.message))
                    }
                }
            }
        }
    }
}