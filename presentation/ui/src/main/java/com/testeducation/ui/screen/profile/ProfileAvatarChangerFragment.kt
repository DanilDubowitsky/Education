package com.testeducation.ui.screen.profile

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.profile.avatar.ProfileAvatarChangerState
import com.testeducation.screen.profile.avatar.ProfileAvatarChangerViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentAvatarChangerBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class ProfileAvatarChangerFragment :
    ViewModelHostFragment<ProfileAvatarChangerViewModel, FragmentAvatarChangerBinding>(
        ProfileAvatarChangerViewModel::class,
        FragmentAvatarChangerBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorBackground)
        binding {
            btnSave.setOnClickListener {

            }
            back.setOnClickListener {
                viewModel.back()
            }
            avatarChanger.changeAvatar = {
                viewModel.changeAvatarSelected(it)
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: ProfileAvatarChangerState) = binding {
        avatarChanger.setItems(state.avatarItemList)
    }

}