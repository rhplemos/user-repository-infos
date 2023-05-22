package com.example.user_repository_infos.scenes.gitHubService

import com.example.user_repository_infos.scenes.gitHubService.models.Repository
import com.example.user_repository_infos.scenes.gitHubService.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("repositories")
    fun getRepositories(): Call<List<Repository>>

    @GET("users/{owner}/repos")
    fun getRepository(
        @Path("owner") owner: String
    ): Call<List<Repository>>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Call<UserModel>

    @GET("users")
    fun getUsers(): Call<UserModel>
}
