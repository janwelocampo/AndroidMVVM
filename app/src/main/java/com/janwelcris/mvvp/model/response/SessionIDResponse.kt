package com.janwelcris.mvvp.model.response

import com.google.gson.annotations.Expose

data class SessionIDResponse (
    @Expose val guest_session_id: String,
    @Expose val expires_at: String
) : BaseResponse()