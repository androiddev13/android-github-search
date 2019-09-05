package com.githubsearch.presentation.githubsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubsearch.R
import com.githubsearch.data.model.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_repository.view.*
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.SpannableString
import androidx.core.content.ContextCompat
import com.githubsearch.toString

class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(repository: Repository?, query: String) {
        repository?.let {
            itemView.titleTextView.text = it.owner.login

            val repoName = SpannableString(it.fullName)
            val index = it.fullName.lastIndexOf(string = query, ignoreCase = true)
            if (index != -1) {
                repoName.setSpan(ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.colorAccent)),
                                index, index + query.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                itemView.subtitleTextView.text = repoName
            }

            if (it.owner.avatarUrl.isNotEmpty()) {
                Picasso.with(itemView.context)
                    .load(it.owner.avatarUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(itemView.avatarImageView)
            } else {
                Picasso.with(itemView.context)
                    .load(R.mipmap.ic_launcher)
                    .into(itemView.avatarImageView)
            }

            itemView.dateTextView.text = it.updatedAt.toString(TEMPLATE_CREATED_AT_DATE)
        }
    }

    companion object {

        private const val TEMPLATE_CREATED_AT_DATE = "yyyy, MM/dd HH:mm"

        fun create(parent: ViewGroup): RepositoryViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_repository, parent, false)
            return RepositoryViewHolder(view)
        }
    }

}