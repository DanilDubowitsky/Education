package com.testeducation.ui.screen.tests.liked

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.screen.tests.liked.LikedTestsState
import com.testeducation.screen.tests.liked.LikedTestsViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentLikedTestsBinding
import com.testeducation.ui.delegates.tests.createTestShortAdapterDelegate
import com.testeducation.ui.screen.tests.list.TestShortDiffUtil
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import org.orbitmvi.orbit.viewmodel.observe

class LikedTestsFragment : ViewModelHostFragment<LikedTestsViewModel, FragmentLikedTestsBinding>(
    LikedTestsViewModel::class,
    FragmentLikedTestsBinding::inflate
) {

    private val testsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortAdapterDelegate(viewModel::toggleTestLike)
        )
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = requireContext().loadColor(R.color.colorRed)
    }

    override fun onDestroy() {
        requireActivity().window.statusBarColor = requireContext().loadColor(
            android.R.color.transparent
        )
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: LikedTestsState) {
        testsAdapter.items = state.tests
    }

    private fun setupViews() = binding {
        testsRecycler.adapter = testsAdapter
        testsRecycler.disableChangeAnimation()
    }

    private fun setupListeners() = binding {

    }

}
