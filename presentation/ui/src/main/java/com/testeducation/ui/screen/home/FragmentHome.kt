package com.testeducation.ui.screen.home

import android.os.Bundle
import android.view.View
import com.testeducation.navigation.core.IScreenAdapter
import com.testeducation.navigation.core.NavigationHost
import com.testeducation.navigation.core.Navigator
import com.testeducation.screen.home.HomeState
import com.testeducation.screen.home.HomeViewModel
import com.testeducation.screen.home.HomeViewModel.Companion.HOME_NAVIGATOR_KEY
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentHomeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
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
            screenAdapter
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.navigateToMain()
        }
        setupListeners()
        observeData()
    }

    private fun setupListeners() = binding {
        btnHome.setClickListener(viewModel::navigateToMain)
        btnFavorites.setClickListener(viewModel::navigateToFavorites)
        btnPlus.setClickListener(viewModel::navigateToCreation)
        btnSettings.setClickListener(viewModel::navigateToSettings)
        btnProfile.setClickListener(viewModel::navigateToProfile)
    }

    private fun observeData() {
        viewModel.observe(this, ::render)
    }

    private fun render(state: HomeState) = binding {
        btnHome.switchEnabledState(true, R.drawable.ic_navigation_home)
        btnFavorites.switchEnabledState(true, R.drawable.ic_navigation_favorites)
        btnProfile.switchEnabledState(true, R.drawable.ic_navigation_profile)
        btnSettings.switchEnabledState(true, R.drawable.ic_navigation_settings)
        when (state.selectedNavigationItem) {
            HomeState.NavigationItem.MAIN -> btnHome.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_navigation_home_filled
            )

            HomeState.NavigationItem.FAVORITES -> btnFavorites.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_navigation_favorites_filled
            )

            HomeState.NavigationItem.SETTINGS -> btnSettings.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_navigation_settings_filled
            )

            HomeState.NavigationItem.PROFILE -> btnProfile.switchEnabledState(
                false,
                disableIcon = R.drawable.ic_navigation_profile_filled
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