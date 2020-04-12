package dev.haenara.githubsearch.ui

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addScrollFinishedListener(listener : ()->Unit) {
    addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0 && !recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                listener()
            }
        }
    })
}
