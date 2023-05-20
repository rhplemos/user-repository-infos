package com.example.user_repository_infos.scenes.userInfo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user_repository_infos.R
import com.example.user_repository_infos.databinding.ActivityUserBinding
import com.example.user_repository_infos.scenes.gitHubService.ApiClient
import com.example.user_repository_infos.scenes.gitHubService.GitHubService
import com.example.user_repository_infos.scenes.gitHubService.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupClickListeners()
    }

    private fun setupClickListeners() = binding.run {
        buttonRefresh.setOnClickListener {
            getRepositories()
        }
    }

    private fun getRepositories() {
        val retrofitClient = ApiClient.getRetrofitInstance("https://api.github.com/")
        val endpoint = retrofitClient.create(GitHubService::class.java)
        val callback = endpoint.getRepositories()

        val recyclerViewUsers = findViewById<RecyclerView>(R.id.recyclerViewUsers)


        callback.enqueue(object: Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                val repositories = response.body()
                val userAdapter = repositories?.let { UserRepositoryAdapter(it) }
                recyclerViewUsers.adapter = userAdapter
                recyclerViewUsers.layoutManager = LinearLayoutManager(this@UserInfoActivity)
            }
        })


    }
}
