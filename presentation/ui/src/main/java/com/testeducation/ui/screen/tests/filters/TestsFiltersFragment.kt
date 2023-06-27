package com.testeducation.ui.screen.tests.filters

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.screen.tests.filters.TestsFiltersViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestsFiltersBinding
import com.testeducation.ui.delegates.tests.createThemeShortAdapterDelegate
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

class TestsFiltersFragment : ViewModelHostFragment<TestsFiltersViewModel, FragmentTestsFiltersBinding>(
    TestsFiltersViewModel::class,
    FragmentTestsFiltersBinding::inflate
) {

    private val themeAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(ThemeShortUI::id),
            createThemeShortAdapterDelegate(onClick = viewModel::selectTheme)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
        setupViews()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestsFiltersState) {
        themeAdapter.items = state.themes
    }

    private fun setupViews() = binding {
        themesRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        themesRecycler.adapter = themeAdapter
        themesRecycler.disableChangeAnimation()
    }

    private fun setupListeners() = binding {
        btnClose.setClickListener(viewModel::exit)
    }

}
