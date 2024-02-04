package com.testeducation.logic.model.test

sealed interface TestSortUI {
    val title: String
    val isChecked: Boolean
    val name: String

    data class OnNameDescending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

    data class OnNameAscending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

    data class OnCreationDateDescending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

    data class OnCreationDateAscending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

    data class OnQuestionsCountDescending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

    data class OnQuestionsCountAscending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

    data class OnPublishDateDescending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

    data class OnPublishDateAscending(
        override val title: String,
        override val isChecked: Boolean,
        override val name: String
    ) : TestSortUI

}
