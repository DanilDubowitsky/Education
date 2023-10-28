package com.testeducation.ui.screen.tests.edit

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.TestSettingsElementUi
import com.testeducation.logic.screen.tests.settings.TestSettingsState
import com.testeducation.screen.tests.edit.settings.TestSettingsViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestSettingsBinding
import com.testeducation.ui.delegates.tests.settings.footerEmpty
import com.testeducation.ui.delegates.tests.settings.testSettingsChoice
import com.testeducation.ui.delegates.tests.settings.testSettingsDesign
import com.testeducation.ui.delegates.tests.settings.testSettingsHorizontalScroll
import com.testeducation.ui.delegates.tests.settings.testSettingsInputTest
import com.testeducation.ui.delegates.tests.settings.testSettingsSelectable
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.simpleDiffUtil

class TestSettingsFragment :
    ViewModelHostFragment<TestSettingsViewModel, FragmentTestSettingsBinding>(
        TestSettingsViewModel::class,
        FragmentTestSettingsBinding::inflate
    ) {

    private val settingsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(TestSettingsElementUi::id),
            testSettingsInputTest(viewModel::updateTextInput),
            testSettingsDesign(viewModel::openTestStyleChanger),
            testSettingsHorizontalScroll(viewModel::updateHorizontal),
            testSettingsChoice(viewModel::updateChoice),
            testSettingsSelectable(viewModel::updateSelectable),
            footerEmpty()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorBackground)
        binding {
            rvSettings.apply {
                adapter = settingsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                itemAnimator = null
            }
            btnSave.setOnClickListener {
                viewModel.saveSettings()
            }
            toolbar.setNavigationOnClickListener {
                viewModel.exit()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestSettingsState) = binding {
        settingsAdapter.items = state.testSettingsElements
    }

}