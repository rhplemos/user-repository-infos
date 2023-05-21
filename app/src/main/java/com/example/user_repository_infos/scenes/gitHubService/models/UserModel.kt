package com.example.user_repository_infos.scenes.gitHubService.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("public_repos")
    val publicRepos: String,
    @SerializedName("company")
    val company: String,
)

