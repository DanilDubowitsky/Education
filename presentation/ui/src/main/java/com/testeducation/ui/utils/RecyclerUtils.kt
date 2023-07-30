package com.testeducation.ui.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.viewbinding.ViewBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

inline fun <reified I : T, T, V : ViewBinding> simpleDelegateAdapter(
    noinline viewBinding: (LayoutInflater, ViewGroup, Boolean) -> V,
    noinline bind: AdapterDelegateViewBindingViewHolder<I, V>.() -> Unit
): AdapterDelegate<List<T>> = adapterDelegateViewBinding(
    viewBinding = { layoutInflater, parent ->
        viewBinding(layoutInflater, parent, false)
    }, block = bind
)

fun <T : Any> simpleDiffUtil(
    areItemsTheSame: (oldItem: T, newItem: T) -> Boolean
): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            areItemsTheSame(oldItem, newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
    }
}

fun <T : Any, R> simpleDiffUtil(
    areItemsTheSame: T.() -> R
): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.areItemsTheSame() == newItem.areItemsTheSame()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
    }
}

inline fun RecyclerView.addPageScrollListener(
    threshold: Int,
    crossinline onNextPage: () -> Unit
): OnScrollListener {
    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) return

            val totalItemCount = layoutManager!!.itemCount
            if (threshold >= totalItemCount) return

            val layoutManager = layoutManager as LinearLayoutManager
            val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

            if (lastVisiblePosition >= totalItemCount - threshold) {
                onNextPage()
            }
        }
    }
    addOnScrollListener(scrollListener)
    return scrollListener
}
