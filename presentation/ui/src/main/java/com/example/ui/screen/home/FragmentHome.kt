package com.example.ui.screen.home

import com.example.screen.home.HomeViewModel
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentHomeBinding

class FragmentHome : ViewModelHostFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class,
    FragmentHomeBinding::inflate
) {
}
