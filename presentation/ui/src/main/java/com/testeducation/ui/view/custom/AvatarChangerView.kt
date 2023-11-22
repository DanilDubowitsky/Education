package com.testeducation.ui.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.AvatarItemUI
import com.testeducation.ui.databinding.ViewAvatarChangerBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDiffUtil

class AvatarChangerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = ViewAvatarChangerBinding.inflate(LayoutInflater.from(context), this, true)

    var changeAvatar: ((Int) -> Unit)? = null

    private val iconDesignAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(AvatarItemUI::id),
            avatarChangerViewAdapterDelegate {
                changeAvatar?.invoke(it)
            }
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding {
            rvAvatar.apply {
                adapter = iconDesignAdapter
                layoutManager = GridLayoutManager(context, 3)
                itemAnimator = null
            }
        }
    }

    fun setItems(avatarItemList: List<AvatarItemUI>) {
        iconDesignAdapter.items = avatarItemList
        avatarItemList.find { it.isSelected }?.let { avatarInfo ->
            binding.imgCurrentAvatar.setBackgroundResource(avatarInfo.avatarDrawableId)
        }
    }
}