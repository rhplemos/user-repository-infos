package com.example.user_repository_infos.scenes.findRespository

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user_repository_infos.databinding.ActivityRepositoryBinding
import org.koin.android.ext.android.inject

class FindRepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepositoryBinding
    private val viewModel: FindRepositoryViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupClickListeners()
        observeRepositories()
    }

    private fun setupClickListeners() = binding.run {
        searchBTN.setOnClickListener {
            searchAction()
        }
    }

    private fun searchAction() = binding.run {
        viewModel.loadAllRepositories(this@FindRepositoryActivity, editTextSearch.text.toString())
    }

    private fun observeRepositories() {
        viewModel.repositories.observe(this) { repositories ->
            val userAdapter = repositories?.let { UserRepositoryAdapter(it) }
            binding.recyclerViewUsers.adapter = userAdapter
            binding.recyclerViewUsers.layoutManager =
                LinearLayoutManager(this@FindRepositoryActivity)
        }
    }
}
