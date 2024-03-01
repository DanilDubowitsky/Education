package com.testeducation.ui.screen.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.profile.about.AboutAppSideEffect
import com.testeducation.logic.screen.profile.about.AboutAppState
import com.testeducation.screen.profile.about.AboutAppViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentAboutAppBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class AboutAppFragment: ViewModelHostFragment<AboutAppViewModel, FragmentAboutAppBinding>(
    AboutAppViewModel::class,
    FragmentAboutAppBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorWhite)
        binding {
            btnClose.setClickListener(viewModel::close)
            tvVersion.text = getString(R.string.about_app_version, "1.0")
            btnVk.setOnClickListener {
                val startIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/testoria_app"))
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(startIntent)
            }
            btnTelegram.setOnClickListener {
                val startIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/testoriaapp"))
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(startIntent)
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render, ::sideEffect)

    private fun render(state: AboutAppState) = binding {

    }

    private fun sideEffect(effect: AboutAppSideEffect) {

    }
}