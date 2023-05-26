package com.example.ui.screen.tests

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.example.logic.model.test.TestShortUI
import com.example.logic.screen.tests.TestsState
import com.example.screen.tests.TestsViewModel
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentTestsBinding
import com.example.ui.delegates.tests.createTestShortAdapterDelegate
import com.example.ui.utils.invoke
import com.example.ui.utils.observe
import com.example.ui.utils.simpleDiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class TestsFragment : ViewModelHostFragment<TestsViewModel, FragmentTestsBinding>(
    TestsViewModel::class,
    FragmentTestsBinding::inflate
) {

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(TestShortUI::id),
            createTestShortAdapterDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        observeData()
    }

    private fun observeData() {
        viewModel.observe(this, ::render)
    }

    private fun setupRecycler() {
        binding.testsRecycler.adapter = adapter
    }

    private fun render(state: TestsState) = binding {
        txtUserName.text = state.userName
        rootGroup.isGone = state.isLoading
        globalProgress.isGone = !state.isLoading
        adapter.items = state.tests
    }

}
