package com.example.user_repository_infos.scenes.findUserInfo

import FindUserInfoViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.user_repository_infos.databinding.ActivityUserBinding
import com.example.user_repository_infos.scenes.gitHubService.models.UserModel
import com.example.user_repository_infos.utils.orUnknown
import org.koin.android.ext.android.inject

class FindUserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val viewModel: FindUserInfoViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupClickListeners()
        observeRepositories()
        setupObserversUiState()
        setupObserversEvent()
        setupUserInfosVisibility()
    }

    override fun onResume() {
        super.onResume()
        setupUserInfosVisibility()
    }

    private fun setupClickListeners() = binding.run {
        searchBTN.setOnClickListener {
            searchAction()
        }
    }

    private fun setupUserInfosVisibility() = binding.run {
        userInfosLL.visibility = View.GONE
        viewModel.shouldShowUserInfos.observe(this@FindUserInfoActivity) { shouldShow ->
            if (shouldShow) {
                userInfosLL.visibility = View.VISIBLE
            }
        }
    }

    private fun searchAction() = binding.run {
        val userName = editTextSearch.text.toString()
        if (userName.isNotBlank()) {
            viewModel.loadUser(userName)
        }
    }

    private fun observeRepositories() {
        viewModel.user.observe(this@FindUserInfoActivity) { user ->
            formatUserInfos(user)
        }
    }

    private fun formatUserInfos(user: UserModel) = binding.run {
        val name = user.name.orUnknown()
        val bio = user.bio.orUnknown()
        val location = user.location.orUnknown()
        val repositoriesQt = user.publicRepos.orUnknown()
        val company = user.company.orUnknown()

        userNameTV.text = name
        userBioTV.text = bio
        userLocationTV.text = location
        repositoryQuantityTV.text = repositoriesQt
        companyTV.text = company
    }

    private fun showLoadingIndicator() = binding.run {
        loadingImageView.visibility = View.VISIBLE
        binding.userInfosLL.visibility = View.GONE
    }

    private fun closeLoadingDialog() = binding.run {
        loadingImageView.visibility = View.GONE
        binding.userInfosLL.visibility = View.VISIBLE
    }

    private fun setupObserversUiState() = viewModel.uiState.run {
        enableLoadIndicator.observe(this@FindUserInfoActivity) { shouldEnable ->
            if (shouldEnable) {
                showLoadingIndicator()
            }
        }

        closeLoadDialog.observe(this@FindUserInfoActivity) { shouldClose ->
            if (shouldClose) {
                closeLoadingDialog()
            }
        }
    }

    private fun setupObserversEvent() = viewModel.event.observe(this) {
        when (it) {
            is FindUserInfoViewModel.FindUserEvent.HandleError -> {
                Toast.makeText(this@FindUserInfoActivity, it.errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }

            else -> {}
        }
    }
}
