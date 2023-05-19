package com.example.ui.screen.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import com.example.navigation.core.IScreenAdapter
import com.example.navigation.core.NavigationHost
import com.example.navigation.core.Navigator
import com.example.screen.home.HomeState
import com.example.screen.home.HomeViewModel
import com.example.screen.home.HomeViewModel.Companion.HOME_NAVIGATOR_KEY
import com.example.ui.R
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentHomeBinding
import com.example.ui.utils.FragmentUtils.invoke
import com.example.ui.utils.FragmentUtils.observe
import com.example.ui.utils.ViewUtils.setClickListener
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
        when (state.selectedNavigationItem) {
            HomeState.NavigationItem.MAIN -> TODO()
            HomeState.NavigationItem.FAVORITES -> TODO()
            HomeState.NavigationItem.SETTINGS -> TODO()
            HomeState.NavigationItem.PROFILE -> TODO()
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
