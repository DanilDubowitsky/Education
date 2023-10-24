package com.testeducation.ui.screen.tests.pass

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.pass.TestPassingSideEffect
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.screen.tests.pass.TestPassingViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPassBinding
import com.testeducation.ui.helper.TimeHandler
import com.testeducation.ui.utils.observe
import java.text.SimpleDateFormat

class TestPassingFragment : ViewModelHostFragment<TestPassingViewModel, FragmentTestPassBinding>(
    TestPassingViewModel::class,
    FragmentTestPassBinding::inflate
) {

    private val timer = TimeHandler()

    private val timeFormatter by lazy {
        SimpleDateFormat()
    }

    private val answersAdapter by lazy {

    }

    private val answersMatchAdapter by lazy {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun onSideEffect(sideEffect: TestPassingSideEffect) {

    }

    private fun render(state: TestPassingState) {

    }

    private fun FragmentTestPassBinding.startTest(time: Long) {
        timer.start(time, TIME_INTERVAL, TEST_TIMER_KEY)
        timer.setOnUpdateListener(TEST_TIMER_KEY) { remainingTime ->

        }
    }

    private fun FragmentTestPassBinding.startQuestion(time: Long) {
        timer.start(time, TIME_INTERVAL, QUESTION_TIMER_KEY)
        timer.setOnUpdateListener(QUESTION_TIMER_KEY) { remainingTime ->

        }
    }

    override fun onDestroy() {
        timer.releaseAll(QUESTION_TIMER_KEY, TEST_TIMER_KEY)
        super.onDestroy()
    }

    private companion object {
        const val QUESTION_TIMER_KEY = "QUESTION_TIMER"
        const val TEST_TIMER_KEY = "TEST_TIMER"
        const val TIME_INTERVAL = 1000L
    }

}
