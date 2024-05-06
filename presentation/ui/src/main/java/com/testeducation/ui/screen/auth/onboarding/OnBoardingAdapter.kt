package com.testeducation.ui.screen.auth.onboarding

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.testeducation.logic.model.auth.OnBoardingItemUi
import com.testeducation.ui.databinding.ItemOnboardingBinding

class OnBoardingAdapter(var list: List<OnBoardingItemUi>, val getDrawable: (Int) -> Drawable): RecyclerView.Adapter<OnBoardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int  = list.count()

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.itemViewBinding.run {
            tvTitle.text = list[position].title
            tvDescription.text = list[position].description
            mainContainer.setBackgroundColor(list[position].color)
            img.setImageDrawable(getDrawable(list[position].image))
        }
    }

    fun updateList(list: List<OnBoardingItemUi>) {
        this.list = list
        notifyDataSetChanged()
    }

    class OnboardingViewHolder(val itemViewBinding: ItemOnboardingBinding): RecyclerView.ViewHolder(itemViewBinding.root)

}