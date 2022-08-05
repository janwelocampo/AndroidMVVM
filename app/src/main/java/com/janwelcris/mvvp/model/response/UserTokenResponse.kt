package com.janwelcris.mvvp.model.response

import com.google.gson.annotations.Expose

data class UserTokenResponse (
    @Expose val request_token: String,
    @Expose val expires_at: String
) : BaseResponse()