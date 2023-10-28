package com.testeducation.ui.screen.tests.edit

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.screen.tests.settings.TestStyleChangerState
import com.testeducation.screen.tests.edit.style.TestStyleChangerModelState
import com.testeducation.screen.tests.edit.style.TestStyleChangerViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentStyleChangerBinding
import com.testeducation.ui.delegates.tests.testCreationBackgroundIconDelegates
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

class TestStyleChangerFragment :
    ViewModelHostFragment<TestStyleChangerViewModel, FragmentStyleChangerBinding>(
        TestStyleChangerViewModel::class,
        FragmentStyleChangerBinding::inflate
    ) {

    private val iconDesignAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(IconDesignItem::style),
            testCreationBackgroundIconDelegates(viewModel::changeStyleTestCard)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding {
            rvIcon.apply {
                adapter = iconDesignAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
                itemAnimator = null
            }
            firstColor.setClickListener {
                viewModel.changeColor(colorState = TestStyleChangerModelState.ColorState.GREEN)
            }
            secondColor.setClickListener {
                viewModel.changeColor(colorState = TestStyleChangerModelState.ColorState.BLUE)
            }
            threeColor.setClickListener {
                viewModel.changeColor(colorState = TestStyleChangerModelState.ColorState.RED)
            }
            fourColor.setClickListener {
                viewModel.changeColor(colorState = TestStyleChangerModelState.ColorState.ORANGE)
            }
            btnSave.setOnClickListener {
                viewModel.update()
            }
            toolbar.setNavigationOnClickListener {
                viewModel.exit()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestStyleChangerState) = binding {
        iconDesignAdapter.items = state.iconDesignList
        cardTest.setContent(state.testShortUI)
    }

}