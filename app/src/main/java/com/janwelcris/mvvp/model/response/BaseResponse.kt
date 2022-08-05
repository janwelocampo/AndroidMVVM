package com.janwelcris.mvvp.model.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
open class BaseResponse(
    @Expose open var success: String? = null) : Parcelable
