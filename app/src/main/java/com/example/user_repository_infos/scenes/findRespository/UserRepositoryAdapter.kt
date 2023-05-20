package com.example.user_repository_infos.scenes.findRespository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.user_repository_infos.R
import com.example.user_repository_infos.scenes.gitHubService.Repository

class UserRepositoryAdapter(private val userList: List<Repository>) : RecyclerView.Adapter<UserRepositoryAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(repository: Repository) {
            val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
            nameTextView.text = repository.name
        }
    }
}
