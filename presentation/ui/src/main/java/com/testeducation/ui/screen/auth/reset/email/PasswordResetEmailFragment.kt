package com.testeducation.ui.screen.auth.reset.email

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.testeducation.logic.screen.auth.reset.email.PasswordResetEmailState
import com.testeducation.screen.auth.reset.email.PasswordResetEmailViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentPasswordResetEmailBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.trimmedTextOrEmpty

class PasswordResetEmailFragment : ViewModelHostFragment<
        PasswordResetEmailViewModel,
        FragmentPasswordResetEmailBinding>(
    PasswordResetEmailViewModel::class,
    FragmentPasswordResetEmailBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: PasswordResetEmailState) = binding {
        rootForm.isGone = state.isLoading
        globalProgress.isGone = !state.isLoading
    }

    private fun setupListeners() = binding {
        txtEmail.addTextChangedListener {
            viewModel.onInputEmailChanged(txtEmail.trimmedTextOrEmpty)
        }
        btnSend.setClickListener(viewModel::send)
    }

}
