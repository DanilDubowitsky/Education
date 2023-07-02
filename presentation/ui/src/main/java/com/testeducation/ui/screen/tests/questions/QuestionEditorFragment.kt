package com.testeducation.ui.screen.tests.questions

import com.testeducation.screen.tests.questions.QuestionEditorViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentQuestionEditorBinding

class QuestionEditorFragment :
    ViewModelHostFragment<QuestionEditorViewModel, FragmentQuestionEditorBinding>(
        QuestionEditorViewModel::class,
        FragmentQuestionEditorBinding::inflate
    ) {


}