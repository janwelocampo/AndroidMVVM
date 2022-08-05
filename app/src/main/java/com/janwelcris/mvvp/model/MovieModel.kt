package com.janwelcris.mvvp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    @Expose val poster_path: String? = null,
    @Expose val adult: Boolean,
    @Expose val overview: String,
    @Expose val release_date: String
) : Parcelable