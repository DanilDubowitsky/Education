package com.testeducation.ui.screen.auth.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.testeducation.logic.model.auth.OnBoardingItemUi
import com.testeducation.logic.screen.auth.onboarding.OnBoardingState
import com.testeducation.screen.auth.onboarding.OnBoardingViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentOnboardingBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class OnBoardingFragment: ViewModelHostFragment<OnBoardingViewModel, FragmentOnboardingBinding>(
    OnBoardingViewModel::class,
    FragmentOnboardingBinding::inflate
) {

    private val adapter by lazy {
        OnBoardingAdapter(emptyList()) {
            ContextCompat.getDrawable(requireContext(), it)!!
        }
    }

    private val colorBackground = listOf(
        R.color.onboarding_green_first,
        R.color.onboarding_green_second,
        R.color.onboarding_green_three,
        R.color.onboarding_green_four,
    )

    private var list: List<OnBoardingItemUi> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            viewPager.run {
                this.adapter = this@OnBoardingFragment.adapter
                isUserInputEnabled = false
            }
            btnNext.setClickListener {
                val nextItemVP = viewPager.currentItem + 1
                container.setBackgroundColor(requireContext().getColor(colorBackground[nextItemVP]))
                requireActivity().window.statusBarColor = requireContext().getColor(colorBackground[nextItemVP])
                val newList = list.mapIndexed { index, item ->
                    if (index == viewPager.currentItem) item.copy(color = list[nextItemVP].color)
                    else item
                }
                adapter.updateList(newList)
                viewPager.setCurrentItem(nextItemVP, true)
            }
            requireActivity().window.statusBarColor = requireContext().loadColor(colorBackground.first())
            observeData()
        }
    }
    private fun observeData() {
        viewModel.observe(this, ::render)
    }

    private fun render(state: OnBoardingState) = binding {
        adapter.updateList(state.list)
        list = state.list
    }
}