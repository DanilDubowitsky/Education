package com.testeducation.ui.helper.extractor

import com.testeducation.helper.resource.SortStringResource
import com.testeducation.ui.R

fun SortStringResource.extractResourceId() = when (this) {
    SortStringResource.CreationDateAscending -> R.string.tests_sort_creation_date_ascending
    SortStringResource.CreationDateDescending -> R.string.tests_sort_creation_date_descending
    SortStringResource.NameAscending -> R.string.tests_sort_name_ascending
    SortStringResource.NameDescending -> R.string.tests_sort_name_descending
    SortStringResource.PublishDateAscending -> R.string.tests_sort_publish_date_ascending
    SortStringResource.PublishDateDescending -> R.string.tests_sort_publish_date_descending
    SortStringResource.QuestionsCountAscending -> R.string.tests_sort_questions_count_ascending
    SortStringResource.QuestionsCountDescending -> R.string.tests_sort_questions_count_descending
}
