package com.testeducation.ui.screen.tests.filters

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.screen.tests.filters.TestsFiltersViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestsFiltersBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class TestsFiltersFragment : ViewModelHostFragment<TestsFiltersViewModel, FragmentTestsFiltersBinding>(
    TestsFiltersViewModel::class,
    FragmentTestsFiltersBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestsFiltersState) {

    }

    private fun setupListeners() = binding {
        btnClose.setClickListener(viewModel::exit)
    }

}
