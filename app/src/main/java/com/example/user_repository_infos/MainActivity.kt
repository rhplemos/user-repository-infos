package com.example.user_repository_infos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.user_repository_infos.databinding.ActivityMainBinding
import com.example.user_repository_infos.scenes.userInfo.UserInfoActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupClickListeners()
    }

    private fun setupClickListeners() = binding.run {
        seeUserInfoBTN.setOnClickListener {
            openUserInfoActivity()
        }
    }

    private fun openUserInfoActivity() {
        val intent = Intent(this, UserInfoActivity::class.java)
        startActivity(intent)
    }
}