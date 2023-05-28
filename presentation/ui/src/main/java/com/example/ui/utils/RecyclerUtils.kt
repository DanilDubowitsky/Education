package com.example.ui.utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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
