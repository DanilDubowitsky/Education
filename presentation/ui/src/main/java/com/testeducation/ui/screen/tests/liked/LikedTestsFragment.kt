package com.testeducation.ui.screen.tests.liked

import com.testeducation.screen.tests.liked.LikedTestsViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentLikedTestsBinding

class LikedTestsFragment : ViewModelHostFragment<LikedTestsViewModel, FragmentLikedTestsBinding>(
    LikedTestsViewModel::class,
    FragmentLikedTestsBinding::inflate
) {



}
