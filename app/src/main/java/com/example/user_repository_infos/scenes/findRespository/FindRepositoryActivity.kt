package com.example.user_repository_infos.scenes.findRespository

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.user_repository_infos.databinding.ActivityUserBinding
import org.koin.android.ext.android.inject

class FindRepositoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val viewModel: FindRepositoryViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
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
        if (binding.editTextSearch.text.isEmpty()) {
            viewModel.loadRepositories(this@FindRepositoryActivity)
        }
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
