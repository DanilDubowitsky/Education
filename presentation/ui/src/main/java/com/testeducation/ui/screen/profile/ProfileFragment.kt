package com.testeducation.ui.screen.profile

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.profile.ProfileState
import com.testeducation.screen.profile.ProfileViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentProfileBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class ProfileFragment: ViewModelHostFragment<ProfileViewModel, FragmentProfileBinding>(
    ProfileViewModel::class,
    FragmentProfileBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorDarkGreen)
        binding {
            tvEdit.setOnClickListener {
                viewModel.navigateToEdit()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: ProfileState) = binding {
        tvUserName.text = state.userName
        tvCreatedTest.text = state.createdTestCount.toString()
        tvPassTest.text = state.passedTestCount.toString()
    }
}