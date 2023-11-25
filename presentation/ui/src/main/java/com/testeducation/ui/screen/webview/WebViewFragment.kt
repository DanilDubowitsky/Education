package com.testeducation.ui.screen.webview

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.webview.WebViewState
import com.testeducation.screen.webview.WebViewViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentWebViewBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class WebViewFragment : ViewModelHostFragment<WebViewViewModel, FragmentWebViewBinding>(
    WebViewViewModel::class,
    FragmentWebViewBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorWhite)
        binding.back.setClickListener {
            viewModel.back()
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: WebViewState) = binding {
        webView.loadUrl(state.url)
    }
}