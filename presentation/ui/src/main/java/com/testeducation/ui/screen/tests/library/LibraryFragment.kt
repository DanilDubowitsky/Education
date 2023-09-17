package com.testeducation.ui.screen.tests.library

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.screen.tests.library.LibraryState
import com.testeducation.screen.tests.library.LibraryViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentLibraryBinding
import com.testeducation.ui.delegates.tests.createTestShortAdapterDelegate
import com.testeducation.ui.screen.tests.list.TestShortDiffUtil
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class LibraryFragment : ViewModelHostFragment<LibraryViewModel, FragmentLibraryBinding>(
    LibraryViewModel::class,
    FragmentLibraryBinding::inflate
) {

    private val publishedTestsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortAdapterDelegate { }
        )
    }

    private val passedTestsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortAdapterDelegate { }
        )
    }

    private val draftsTestsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            TestShortDiffUtil(),
            createTestShortAdapterDelegate { }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupViews()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: LibraryState) {

    }

    private fun setupViews() = binding {
        draftsPager.adapter = draftsTestsAdapter
        passedPager.adapter = passedTestsAdapter
        publishedPager.adapter = publishedTestsAdapter
    }
}
