package com.abnamro.abnhub.ui.screens.repo_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abnamro.abnhub.R
import com.abnamro.abnhub.databinding.FragmentRepoListBinding
import com.abnamro.abnhub.ui.util.InfiniteScrollListener
import com.abnamro.abnhub.ui.util.launchAndRepeatWithViewLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoListFragment : Fragment() {

    private lateinit var binding: FragmentRepoListBinding

    internal val viewModel: RepoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentRepoListBinding>(
            inflater, R.layout.fragment_repo_list, container, false
        ).apply {
            lifecycleOwner = this@RepoListFragment
            viewModel = this@RepoListFragment.viewModel
        }

        val repoAdapter = RepoAdapter()
        initUI(repoAdapter)

        launchAndRepeatWithViewLifecycle {
            viewModel.uiState.collect { uiState ->
                updateUiOnNewData(uiState, repoAdapter)
            }
        }

        return binding.root
    }

    private fun initUI(repoAdapter: RepoAdapter) {
        binding.swipeRefreshLayout.setOnRefreshListener(viewModel::refreshData)

        binding.repoRecyclerview.apply {
            this.adapter = repoAdapter
            val repoLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            this.layoutManager = repoLayoutManager

            this.addOnScrollListener(object : InfiniteScrollListener(repoLayoutManager) {
                override fun onLoadMore() = viewModel.loadMoreData()
                override fun isDataLoading(): Boolean = viewModel.uiState.value.isLoading
            })
        }
    }

    private fun updateUiOnNewData(
        uiState: RepoListUiState,
        repoAdapter: RepoAdapter
    ) {
        val isShowList = uiState.isLoading || uiState.lstRepos.isNotEmpty()
        binding.isShowList = isShowList

        if (isShowList && uiState.errorMessage != null) {
            Snackbar.make(
                binding.coordinatorLayout,
                uiState.errorMessage,
                Snackbar.LENGTH_INDEFINITE
            )
                .setAction(getString(R.string.ok)) { viewModel.clearErrorMessage() }
                .show()
        }

        if (!uiState.isLoading)
            binding.swipeRefreshLayout.isRefreshing = false

        repoAdapter.submitList(uiState.lstRepos)
    }
}
