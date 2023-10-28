package com.testeducation.ui.screen.home

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.testeducation.logic.screen.home.HomeSideEffect
import com.testeducation.logic.screen.home.HomeState
import com.testeducation.navigation.core.AnimationSet
import com.testeducation.navigation.core.IScreenAdapter
import com.testeducation.navigation.core.NavigationHost
import com.testeducation.navigation.core.Navigator
import com.testeducation.screen.home.HomeViewModel
import com.testeducation.screen.home.HomeViewModel.Companion.HOME_NAVIGATOR_KEY
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentHomeBinding
import com.testeducation.ui.utils.hideView
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showView
import com.testeducation.ui.utils.startAnimation
import com.testeducation.ui.utils.switchEnabledState
import javax.inject.Inject

class FragmentHome : ViewModelHostFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class,
    FragmentHomeBinding::inflate
) {

    @Inject
    lateinit var navigationHost: NavigationHost

    @Inject
    lateinit var screenAdapter: IScreenAdapter

    private val navigator by lazy {
        Navigator(
            requireActivity(),
            R.id.homeFragmentContainer,
            screenAdapter,
            parentFragmentManager,
            moveAnimationSet = AnimationSet(
                com.testeducation.navigation.R.anim.slide_in,
                com.testeducation.navigation.R.anim.fade_out,
                com.testeducation.navigation.R.anim.fade_in,
                com.testeducation.navigation.R.anim.slide_out,
            ),
            replaceAnimationSet = AnimationSet(
                com.testeducation.navigation.R.anim.fade_in,
                com.testeducation.navigation.R.anim.fade_out,
                com.testeducation.navigation.R.anim.fade_in,
                com.testeducation.navigation.R.anim.fade_out
            )
        )
    }

    private val slideUpAnim by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_animation)
    }

    private val slideDown by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down_animation)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
        setupViews()
    }

    private fun setupViews() {
        requireActivity().window.statusBarColor = requireContext().loadColor(
            android.R.color.transparent
        )
    }

    private fun setupListeners() = binding {
        btnHome.setClickListener(viewModel::navigateToTests)
        btnFavorites.setClickListener(viewModel::navigateToFavorites)
        btnPlus.setClickListener(viewModel::navigateToCreation)
        btnLibrary.setClickListener(viewModel::navigateToLibrary)
        btnProfile.setClickListener(viewModel::navigateToProfile)
    }

    private fun observeData() {
        viewModel.observe(this, ::render, ::onSideEffect)
    }

    private fun onSideEffect(sideEffect: HomeSideEffect) {
        when (sideEffect) {
            HomeSideEffect.SlideDownNavigation -> showBottomNavigation()
            HomeSideEffect.SlideUpNavigation -> hideBottomNavigation()
        }
    }

    private fun hideBottomNavigation() = with(binding) {
        if (bottomNavigation.isGone) return@with
        if (slideUpAnim.hasEnded() || !slideUpAnim.hasStarted()) {
            bottomNavigation.startAnimation(slideUpAnim) {
                bottomNavigation.hideView()
            }
        }
    }

    private fun showBottomNavigation() = with(binding) {
        if (bottomNavigation.isVisible) return@with
        if (slideDown.hasEnded() || !slideDown.hasStarted()) {
            bottomNavigation.showView()
            bottomNavigation.startAnimation(
                slideDown
            )
        }
    }

    private fun render(state: HomeState) = binding {
        btnHome.switchEnabledState(true, R.drawable.ic_home_outline)
        btnFavorites.switchEnabledState(true, R.drawable.ic_favorite_outline)
        btnProfile.switchEnabledState(true, R.drawable.ic_profile_outline)
        btnLibrary.switchEnabledState(true, R.drawable.ic_library_outline)
        when (state.selectedNavigationItem) {
            HomeState.NavigationItem.MAIN -> btnHome.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_home_filled
            )

            HomeState.NavigationItem.FAVORITES -> btnFavorites.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_favorite_filled
            )

            HomeState.NavigationItem.LIBRARY -> btnLibrary.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_library_filled
            )

            HomeState.NavigationItem.PROFILE -> btnProfile.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_profile_filled
            )
        }
    }

    override fun onResume() {
        navigationHost.setNavigator(navigator, HOME_NAVIGATOR_KEY)
        super.onResume()
    }

    override fun onPause() {
        navigationHost.removeNavigator(HOME_NAVIGATOR_KEY)
        super.onPause()
    }

}
