package com.testeducation.ui.screen.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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
                viewModel.save()
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
        avatarChanger.isClickable = !state.isLoading
        loadingProgress.isVisible = state.isLoading
        btnSave.text = if (state.isLoading) {
            ""
        } else requireContext().getString(R.string.common_save)
        btnSave.isClickable = !state.isLoading
    }

}