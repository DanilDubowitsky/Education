package com.testeducation.ui.screen.profile

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.profile.edit.ProfileEditState
import com.testeducation.screen.profile.edit.ProfileEditViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentProfileEditBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class ProfileEditFragment : ViewModelHostFragment<ProfileEditViewModel, FragmentProfileEditBinding>(
    ProfileEditViewModel::class,
    FragmentProfileEditBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorBackground)
        binding {
            back.setOnClickListener {
                viewModel.back()
            }
            btnLogOut.setOnClickListener {
                viewModel.logOut()
            }
            tvChangeAvatar.setOnClickListener {
                viewModel.goToAvatarChangerScreen()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: ProfileEditState) = binding {
        editTextEmail.setText(state.email)
        editTextNickName.setText(state.nickName)
        imgAvatar.setImageResource(state.avatar)
    }
}