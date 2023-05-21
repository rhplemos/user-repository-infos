package com.example.user_repository_infos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import appModule
import com.example.user_repository_infos.databinding.ActivityMainBinding
import com.example.user_repository_infos.scenes.findRespository.FindRepositoryActivity
import com.example.user_repository_infos.scenes.findUserInfo.FindUserInfoActivity
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupClickListeners()
    }

    private fun setupClickListeners() = binding.run {
        searchRepositoriesBTN.setOnClickListener {
            openFindRepositoryActivity()
        }
        searchUserBTN.setOnClickListener {
            openUserInfoActivity()
        }
    }

    private fun openFindRepositoryActivity() {
        val intent = Intent(this, FindRepositoryActivity::class.java)
        startActivity(intent)
    }

    private fun openUserInfoActivity() {
        val intent = Intent(this, FindUserInfoActivity::class.java)
        startActivity(intent)
    }
}