package com.udacity.asteroidradar.pojo

import com.google.gson.annotations.SerializedName

data class ExampleErrorResponse(
    val status: String,
    @SerializedName("failure_message")
    val failureMessage: String
)
