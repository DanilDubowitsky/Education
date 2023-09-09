package com.testeducation.ui.screen.tests.edit

import com.testeducation.screen.tests.edit.TestEditorViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestEditorBinding

class TestEditorFragment :
    ViewModelHostFragment<TestEditorViewModel, FragmentTestEditorBinding>(
        TestEditorViewModel::class,
        FragmentTestEditorBinding::inflate
    ) {

}