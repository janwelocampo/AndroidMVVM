package com.janwelcris.mvvp.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.janwelcris.mvvp.R
import com.janwelcris.mvvp.base.BaseFragment
import com.janwelcris.mvvp.databinding.FragmentHomeBinding
import com.janwelcris.mvvp.ui.main.home.adapter.MovieAdapter
import com.janwelcris.mvvp.ui.main.home.adapter.TagsAdapter
import com.janwelcris.mvvp.ui.main.home.moviedetails.MovieDetailsFragment.Companion.MOVIE_DETAILS
import com.janwelcris.mvvp.utils.gone
import com.janwelcris.mvvp.utils.showToast
import com.janwelcris.mvvp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel >() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val viewModel: HomeViewModel by viewModels()

    lateinit var tagsAdapter: TagsAdapter
    lateinit var movieAdapter: MovieAdapter

    override fun onReady(savedInstanceState: Bundle?) {
        initViews()
        initTagObservation()
        initMovieObservation()
    }

    private fun initViews(){
        context?.let { ctx ->
            // Tags RecyclerView
            tagsAdapter = TagsAdapter { tag, _ ->
                performSearch(tag.tagName)
            }
            val flexboxLayoutManager = FlexboxLayoutManager(ctx).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                alignItems = AlignItems.STRETCH
            }
            binding?.recyclerTags?.layoutManager = flexboxLayoutManager
            binding?.recyclerTags?.adapter = tagsAdapter

            // Photos RecyclerView
            movieAdapter = MovieAdapter { it, _ ->
                viewModel.navigate(R.id.action_homeFragment_to_movie_details, bundleOf(MOVIE_DETAILS to it))
            }
            movieAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

           with(binding?.recyclerMovies){
               this?.adapter = movieAdapter
               this?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                   override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                       super.onScrollStateChanged(recyclerView, newState)
                       if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                           viewModel.loadMore()
                       }
                   }
               })
           }

        }
    }

    private fun initTagObservation(){
        viewModel.tagListState.observe(viewLifecycleOwner) {
            when(it){
                ContentTagState ->{
                    binding?.recyclerTags?.visible()
                }
                else -> {
                    binding?.recyclerTags?.gone()
                }
            }
        }

        viewModel.tagListLiveData.observe(viewLifecycleOwner){
            tagsAdapter.updateItems(it)
        }
    }

    private fun initMovieObservation(){
        viewModel.movieListState.observe(viewLifecycleOwner) {
            when(it){
                ContentMovieState -> {
                    binding?.recyclerMovies?.visible()
                    binding?.progressPhotos?.gone()
                }
                EmptyMovieState -> {
                    binding?.recyclerMovies?.gone()
                    binding?.progressPhotos?.gone()
                }
                is ErrorMovieState -> {
                    binding?.recyclerMovies?.gone()
                    binding?.progressPhotos?.gone()
                }
                LoadingMovieNextPageState -> {
                    binding?.recyclerMovies?.visible()
                    binding?.progressPhotos?.visible()
                    showToast(getString(R.string.message_load_more_movies_str))
                }
                LoadingMovieState -> {
                    binding?.recyclerMovies?.gone()
                    binding?.progressPhotos?.visible()
                }
            }
        }

        viewModel.movieListLiveData.observe(viewLifecycleOwner){
            movieAdapter.updateItems(it)
        }
    }

    private fun performSearch(tag: String) {
        movieAdapter.resetAdapter()

        binding?.lblPopular?.text = tag
        viewModel.searchByTag(tag)
    }




}