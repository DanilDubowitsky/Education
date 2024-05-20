package com.testeducation.ui.screen.tests.edit.publish

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.edit.TestPublishSideEffect
import com.testeducation.logic.screen.tests.edit.TestPublishState
import com.testeducation.screen.tests.edit.publish.TestPublishViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTestPublishBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TestPublishDialog :
    ViewModelHostBottomSheetDialog<DialogTestPublishBinding, TestPublishViewModel>(
        TestPublishViewModel::class,
        DialogTestPublishBinding::inflate
    ) {
    private val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
    private val simpleDateFormatTime = SimpleDateFormat("HH:mm", Locale.ROOT)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding {
            rbDraft.setOnClickListener {
                viewModel.changeStatus(TestPublishState.StatusPublish.DRAFT)
            }
            rbPublish.setOnClickListener {
                viewModel.changeStatus(TestPublishState.StatusPublish.PUBLISH)
            }
            rbPending.setOnClickListener {
                viewModel.changeStatus(TestPublishState.StatusPublish.SCHEDULED)
            }
            btnSave.setOnClickListener {
                viewModel.save()
            }
            tvTimeDate.setOnClickListener {
                viewModel.openTimeDateDialog()
            }
            containerTime.setOnClickListener {
                viewModel.openTimeDialog()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun render(state: TestPublishState) = binding {
        rbDraft.isChecked = state.statusPublish == TestPublishState.StatusPublish.DRAFT
        rbPublish.isChecked = state.statusPublish == TestPublishState.StatusPublish.PUBLISH
        rbPending.isChecked = state.statusPublish == TestPublishState.StatusPublish.SCHEDULED
        tvDateFormat.text = simpleDateFormat.format(state.calendar.time)
        tvTimeFormat.text = simpleDateFormatTime.format(state.calendar.time)
    }

    private fun onSideEffect(sideEffect: TestPublishSideEffect) {
        when (sideEffect) {
            is TestPublishSideEffect.OpenDatePicker -> {
                val picker = DatePickerDialog(
                    requireContext(), { _, year, month, dayOfMonth ->
                        viewModel.setDate(year, month, dayOfMonth)
                    }, sideEffect.calendar.get(Calendar.YEAR),
                    sideEffect.calendar.get(Calendar.MONTH),
                    sideEffect.calendar.get(Calendar.DAY_OF_MONTH)
                )
                picker.datePicker.minDate = System.currentTimeMillis()
                picker.show()
            }

            is TestPublishSideEffect.OpenTimePicker -> {
                val picker = TimePickerDialog(
                    requireContext(), { _, hourOfDay, minute ->
                        viewModel.setTime(hourOfDay, minute)
                    }, sideEffect.calendar.get(Calendar.HOUR),
                    sideEffect.calendar.get(Calendar.MINUTE),
                    true
                )
                picker.show()
            }
        }
    }
}