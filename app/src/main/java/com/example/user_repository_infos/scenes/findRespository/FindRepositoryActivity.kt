package com.example.user_repository_infos.scenes.findRespository

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        setupObserversUiState()
        setupObserversEvent()
        setRepositoryVisible()
    }

    private fun setupClickListeners() = binding.run {
        searchBTN.setOnClickListener {
            searchAction()
        }
    }

    private fun setRepositoryVisible() = binding.run {
        recyclerViewUsers.visibility = View.GONE
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

    private fun showLoadingIndicator() = binding.run {
        recyclerViewUsers.visibility = View.GONE
        loadingImageView.visibility = View.VISIBLE
    }

    private fun closeLoadingDialog() = binding.run {
        recyclerViewUsers.visibility = View.VISIBLE
        loadingImageView.visibility = View.GONE
    }

    private fun setupObserversUiState() = viewModel.uiState.run {
        enableLoadIndicator.observe(this@FindRepositoryActivity) { shouldEnable ->
            if (shouldEnable) {
                showLoadingIndicator()
            }
        }

        closeLoadDialog.observe(this@FindRepositoryActivity) { shouldClose ->
            if (shouldClose) {
                closeLoadingDialog()
            }
        }
    }

    private fun errorAction(errorMessage: String) {
        Toast.makeText(this@FindRepositoryActivity, errorMessage, Toast.LENGTH_SHORT).show()
        binding.recyclerViewUsers.visibility = View.GONE
    }

    private fun setupObserversEvent() = viewModel.event.observe(this) {
        when(it) {
            is FindRepositoryViewModel.FindRepositoryEvent.HandleError -> {
                errorAction(it.errorMessage)
            }

            else -> {}
        }
    }
}
