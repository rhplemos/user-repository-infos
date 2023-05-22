package com.example.user_repository_infos.scenes.findUserInfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.user_repository_infos.R
import com.example.user_repository_infos.scenes.gitHubService.models.UserModel

class UserInfoAdapter(private val userList: List<UserModel>) : RecyclerView.Adapter<UserInfoAdapter.UserViewHolder>() {

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

        fun bind(user: UserModel) {
            val nameTextView = itemView.findViewById<TextView>(R.id.userNameTV)
            val locationTextView = itemView.findViewById<TextView>(R.id.userLocationTV)
            val bioTextView = itemView.findViewById<TextView>(R.id.userBioTV)
            val companyTextView = itemView.findViewById<TextView>(R.id.companyTV)
            val quantityTextView = itemView.findViewById<TextView>(R.id.repositoryQuantityTV)

            nameTextView.text = user.name
            locationTextView.text = user.location
            bioTextView.text = user.bio
            companyTextView.text = user.company
            quantityTextView.text = user.publicRepos
        }
    }
}
