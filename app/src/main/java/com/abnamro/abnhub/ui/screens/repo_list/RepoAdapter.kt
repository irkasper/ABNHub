package com.abnamro.abnhub.ui.screens.repo_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abnamro.abnhub.R
import com.abnamro.abnhub.databinding.RepoItemBinding
import com.abnamro.abnhub.domain.entity.Repo

/**
 * adapter class for repo recyclerview
 *
 * implemented ListAdapter which is a RecyclerView.Adapter base class for presenting List data in a RecyclerView,
 * including computing diffs between Lists on a background thread.
 */
class RepoAdapter : ListAdapter<Repo, RepoAdapter.ViewHolder>(
    RepoDiffCallback()
) {

    class ViewHolder(
        private val binding: RepoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                binding.repo?.name?.let {
                    navigateToPlant(it, view)
                }
            }
        }

        private fun navigateToPlant(repoName: String, view: View) {
            val direction = RepoListFragmentDirections
                .actionRepoListFragmentToRepoDetailFragment(repoName = repoName)
            view.findNavController().navigate(direction)
        }

        fun bind(repo: Repo) {
            binding.repo = repo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = R.layout.repo_item
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }
}

private class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

    override fun areItemsTheSame(
        oldItem: Repo,
        newItem: Repo
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Repo,
        newItem: Repo
    ): Boolean {
        return oldItem == newItem
    }
}
