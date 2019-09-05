package com.githubsearch.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubsearch.R
import com.githubsearch.data.model.NetworkStatus
import com.githubsearch.data.model.Status
import kotlinx.android.synthetic.main.item_network_status.view.*

class RepositoryNetworkStatusViewHolder(view: View,  private val onRetryClick: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retryButton.setOnClickListener {
            onRetryClick()
        }
    }

    fun bind(networkStatus: NetworkStatus?) {
        itemView.errorMessageTextView.visibility = if (networkStatus?.message != null) View.VISIBLE else View.GONE
        itemView.errorMessageTextView.text = networkStatus?.message
        itemView.retryButton.visibility = if (networkStatus?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.progressBar.visibility = if (networkStatus?.status == Status.LOADING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, onRetryClick: () -> Unit): RepositoryNetworkStatusViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_network_status, parent, false)
            return RepositoryNetworkStatusViewHolder(view, onRetryClick)
        }
    }

}