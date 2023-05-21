package com.example.user_repository_infos.scenes.findRespository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.user_repository_infos.scenes.gitHubService.ApiClient
import com.example.user_repository_infos.scenes.gitHubService.GitHubService
import com.example.user_repository_infos.scenes.gitHubService.models.Repository
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindRepositoryViewModel : ViewModel(), KoinComponent {
    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    fun loadRepositories(context: Context) {
        val retrofitClient = ApiClient.getRetrofitInstance()
        val endpoint = retrofitClient.create(GitHubService::class.java)
        val callback = endpoint.getRepositories()

        callback.enqueue(object : Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                val repositories = response.body()
                _repositories.value = repositories ?: listOf()
            }
        })
    }
}