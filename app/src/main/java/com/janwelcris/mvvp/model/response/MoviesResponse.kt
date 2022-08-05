package com.janwelcris.mvvp.model.response

import com.google.gson.annotations.Expose
import com.janwelcris.mvvp.model.MovieModel

data class MoviesResponse (
    @Expose val total_pages: Int,
    @Expose val page: Int,
    @Expose val results: ArrayList<MovieModel>
) : BaseResponse()