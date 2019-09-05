package com.githubsearch.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubsearch.R
import com.githubsearch.data.model.Repository
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(repository: Repository?) {
        itemView.repositoryNameTextView.text = repository?.fullName
    }

    companion object {
        fun create(parent: ViewGroup): RepositoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_repository, parent, false)
            return RepositoryViewHolder(view)
        }
    }

}