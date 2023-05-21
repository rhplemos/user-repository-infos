package com.example.user_repository_infos.scenes.findUserInfo

import FindUserInfoViewModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.user_repository_infos.databinding.ActivityUserBinding
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
    }

    private fun setupClickListeners() = binding.run {
        searchBTN.setOnClickListener {
            searchAction()
        }
    }

    private fun searchAction() = binding.run {
        if (binding.editTextSearch.text.isEmpty()) {
            viewModel.loadUser(this@FindUserInfoActivity)
        }
    }
    private fun observeRepositories() = binding.run  {
        viewModel.user.observe(this@FindUserInfoActivity) { user ->
            userNameTV.text = user.name
            userBioTV.text = user.bio
            userLocationTV.text = user.location
//            val userAdapter = user?.let { UserInfoAdapter(it) }
//            binding.recyclerViewUsers.adapter = userAdapter
//            binding.recyclerViewUsers.layoutManager =
//                LinearLayoutManager(this@FindUserInfoActivity)
        }
    }
}
