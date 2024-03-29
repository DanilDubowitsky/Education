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
import com.testeducation.ui.utils.setClickListener

class ProfileFragment: ViewModelHostFragment<ProfileViewModel, FragmentProfileBinding>(
    ProfileViewModel::class,
    FragmentProfileBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.initDataProfile()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorDarkGreen)
        binding {
            tvEdit.setClickListener(viewModel::navigateToEdit)
            problemContainer.setClickListener(viewModel::navigateToSupport)
            securityContainer.setClickListener(viewModel::navigateToPolicies)
            aboutAppContainer.setClickListener(viewModel::navigateAboutApp)
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: ProfileState) = binding {
        tvUserName.text = state.userName
        tvCreatedTest.text = state.createdTestCount.toString()
        tvPassTest.text = state.passedTestCount.toString()
        imgAvatar.setImageResource(state.drawableAvatar)
    }
}