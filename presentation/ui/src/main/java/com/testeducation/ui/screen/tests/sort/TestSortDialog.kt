package com.testeducation.ui.screen.tests.sort

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.OrderDirectionUI
import com.testeducation.logic.model.test.TestSortUI
import com.testeducation.logic.screen.tests.sort.TestSortState
import com.testeducation.screen.tests.sort.TestSortViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTestsSortBinding
import com.testeducation.ui.delegates.tests.sort.createTestSortDelegate
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

class TestSortDialog : ViewModelHostBottomSheetDialog<DialogTestsSortBinding, TestSortViewModel>(
    TestSortViewModel::class,
    DialogTestsSortBinding::inflate
) {

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(TestSortUI::title),
            createTestSortDelegate(::handleSortClick)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
        setupListeners()
    }

    private fun setupViews() {
        binding.recyclerView.disableChangeAnimation()
        binding.recyclerView.adapter = adapter
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestSortState) = binding {
        adapter.items = state.sortFields
    }

    private fun setupListeners() = binding {
        btnShow.setClickListener(viewModel::applySort)
    }

    private fun handleSortClick(item: TestSortUI) {
        when (item) {
            is TestSortUI.OnCreationDateAscending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.ASCENDING.name)
            }
            is TestSortUI.OnCreationDateDescending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.DESCENDING.name)
            }
            is TestSortUI.OnNameAscending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.ASCENDING.name)
            }
            is TestSortUI.OnNameDescending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.DESCENDING.name)
            }
            is TestSortUI.OnPublishDateAscending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.ASCENDING.name)
            }
            is TestSortUI.OnPublishDateDescending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.DESCENDING.name)
            }
            is TestSortUI.OnQuestionsCountAscending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.ASCENDING.name)
            }
            is TestSortUI.OnQuestionsCountDescending -> {
                viewModel.changeSort(item.name, OrderDirectionUI.DESCENDING.name)
            }
        }
    }

}
