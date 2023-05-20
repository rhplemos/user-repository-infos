package com.example.user_repository_infos.scenes.gitHubService

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("htmlUrl")
    val htmlUrl: String
)

