package com.githubsearch.presentation.githubsearch

import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.githubsearch.data.model.NetworkStatus
import com.githubsearch.data.model.Repository
import java.lang.IllegalStateException

class RepositoryAdapter(private val onRetryClick: ()-> Unit)
    : PagedListAdapter<Repository, RecyclerView.ViewHolder>(repositoryDiffCallback) {

    private enum class Item(val value: Int) {
        REPOSITORY(0), NETWORK_STATUS(1)
    }

    private var networkStatus: NetworkStatus? = null

    private var query: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            Item.REPOSITORY.value -> RepositoryViewHolder.create(
                parent
            )
            Item.NETWORK_STATUS.value -> RepositoryNetworkStatusViewHolder.create(
                parent,
                onRetryClick
            )
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            Item.REPOSITORY.value -> (holder as RepositoryViewHolder).bind(getItem(position), query)
            Item.NETWORK_STATUS.value -> (holder as RepositoryNetworkStatusViewHolder).bind(networkStatus)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            Item.NETWORK_STATUS.value
        } else {
            Item.REPOSITORY.value
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return networkStatus != null && networkStatus != NetworkStatus.SUCCESS
    }

    fun setNetworkStatus(newNetworkStatus: NetworkStatus?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = networkStatus
                val hadExtraRow = hasExtraRow()
                networkStatus = newNetworkStatus
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkStatus) {
                    notifyItemChanged(itemCount - 1)
                }
            } else {
                notifyDataSetChanged()
            }
        }
    }

    fun setData(list: PagedList<Repository>, query: String) {
        this.query = query
        submitList(list)
    }

    companion object {
        val repositoryDiffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }
}