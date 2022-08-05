package com.janwelcris.mvvp.ui.main.home.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.navGraphViewModels
import coil.load
import com.janwelcris.mvvp.R
import com.janwelcris.mvvp.base.BaseFragment
import com.janwelcris.mvvp.databinding.FragmentMovieDetailsBinding
import com.janwelcris.mvvp.model.MovieModel

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    override val viewModel: MovieDetailsViewModel by navGraphViewModels(R.id.mobile_navigation)

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate

    override fun onReady(savedInstanceState: Bundle?) {
        val movieDetails = arguments?.getParcelable<MovieModel>(MOVIE_DETAILS)
        if (movieDetails == null){
            viewModel.navigateBack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.navigateBack()
        }

        initObservations()
        viewModel.initMovieDetails(movieDetails!!)
    }

   private fun initObservations() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) { movie ->
            with(binding){

                this?.imgMovieBackground?.load("https://www.themoviedb.org/t/p/w440_and_h660_face" + movie.poster_path) {
                    placeholder(R.color.color_box_background)
                    crossfade(true)
                }
                this?.textDescription?.text = movie.overview
            }
        }
    }



    companion object{
        const val MOVIE_DETAILS = "movie_details"
    }


}