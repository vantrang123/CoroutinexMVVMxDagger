package com.tom.learncoroutinexroom.extensions

import androidx.recyclerview.widget.RecyclerView
import com.tom.learncoroutinexroom.utils.EndlessRecyclerOnScrollListener

fun RecyclerView.onLoadMore(onLoadMore: () -> Unit) {
    this.addOnScrollListener(object: EndlessRecyclerOnScrollListener(){
        override fun onLoadMore() {
            onLoadMore.invoke()
        }
    })
}