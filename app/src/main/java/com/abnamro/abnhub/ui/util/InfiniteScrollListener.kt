package com.abnamro.abnhub.ui.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abnamro.abnhub.utils.VISIBLE_THRESHOLD

abstract class InfiniteScrollListener(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // bail out if scrolling upward or already loading data
        if (dy < 0 || isDataLoading()) return

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            recyclerView.post { onLoadMore() }
        }
    }

    abstract fun onLoadMore()

    abstract fun isDataLoading(): Boolean
}
