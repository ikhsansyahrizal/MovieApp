package com.docotel.ikhsansyahrizal.doconewss.helper

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessOnScrollListener : RecyclerView.OnScrollListener() {
    private var mPreviousTotal = 0
    private var mLoading = true
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            val visibleItemCount = recyclerView.childCount
            val totalItemCount = recyclerView.layoutManager!!.itemCount
            val firstVisibleItem =
                (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
            if (mLoading) {
                if (totalItemCount > mPreviousTotal) {
                    mLoading = false
                    mPreviousTotal = totalItemCount
                }
            }
            val visibleThreshold = 5
            if (!mLoading && totalItemCount - visibleItemCount
                <= firstVisibleItem + visibleThreshold
            ) {
                onLoadMore()
                mLoading = true
            }
        }
    }

    abstract fun onLoadMore()
}
