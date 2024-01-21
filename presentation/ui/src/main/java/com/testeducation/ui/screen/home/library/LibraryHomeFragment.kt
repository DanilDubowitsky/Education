package com.testeducation.ui.screen.home.library

import android.os.Bundle
import android.view.View
import com.testeducation.navigation.core.AnimationSet
import com.testeducation.navigation.core.IScreenAdapter
import com.testeducation.navigation.core.NavigationHost
import com.testeducation.navigation.core.Navigator
import com.testeducation.screen.home.library.LibraryHomeViewModel
import com.testeducation.screen.home.library.LibraryHomeViewModel.Companion.LIBRARY_NAVIGATOR_KEY
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentLibraryContainerBinding
import javax.inject.Inject

class LibraryHomeFragment :
    ViewModelHostFragment<LibraryHomeViewModel, FragmentLibraryContainerBinding>(
        LibraryHomeViewModel::class,
        FragmentLibraryContainerBinding::inflate
    ) {

    @Inject
    lateinit var navigationHost: NavigationHost

    @Inject
    lateinit var screenAdapter: IScreenAdapter

    private val navigator by lazy {
        Navigator(
            requireActivity(),
            R.id.libraryFragmentContainer,
            screenAdapter,
            childFragmentManager,
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToLibrary()
    }

    override fun onResume() {
        navigationHost.setNavigator(navigator, LIBRARY_NAVIGATOR_KEY)
        super.onResume()
    }

    override fun onPause() {
        navigationHost.removeNavigator(LIBRARY_NAVIGATOR_KEY)
        super.onPause()
    }

}
