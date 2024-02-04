package com.testeducation.screen.tests.sort

import com.testeducation.core.IReducer
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.SortStringResource
import com.testeducation.logic.model.test.TestSortUI
import com.testeducation.logic.screen.tests.sort.TestSortState

class TestSortReducer(
    private val resourceHelper: IResourceHelper
) : IReducer<TestSortModelState, TestSortState> {

    override fun reduce(modelState: TestSortModelState): TestSortState {
        return TestSortState(
            sortFields = getSortFieldsUI(
                modelState.selectedField,
                modelState.direction
            )
        )
    }

    private fun getSortFieldsUI(
        orderField: TestOrderField,
        direction: OrderDirection
    ): ArrayList<TestSortUI> {
        val testSorts = ArrayList<TestSortUI>()
        TestOrderField.values().forEach { sort ->
            val isAscending = orderField == sort && direction == OrderDirection.ASCENDING
            val isDescending = orderField == sort && direction == OrderDirection.DESCENDING
            val (ascendingValue, descendingValue) = when (sort) {
                TestOrderField.TITLE -> {
                    TestSortUI.OnNameAscending(
                        resourceHelper.extractStringResource(SortStringResource.NameAscending),
                        isAscending,
                        sort.name
                    ) to TestSortUI.OnNameDescending(
                        resourceHelper.extractStringResource(SortStringResource.NameDescending),
                        isDescending,
                        sort.name
                    )
                }

                TestOrderField.CREATION -> {
                    TestSortUI.OnCreationDateAscending(
                        resourceHelper.extractStringResource(
                            SortStringResource.CreationDateAscending
                        ),
                        isAscending,
                        sort.name
                    ) to TestSortUI.OnCreationDateDescending(
                        resourceHelper.extractStringResource(
                            SortStringResource.CreationDateDescending
                        ),
                        isDescending,
                        sort.name
                    )
                }

                TestOrderField.PUBLISHED -> {
                    TestSortUI.OnPublishDateAscending(
                        resourceHelper.extractStringResource(
                            SortStringResource.PublishDateAscending
                        ),
                        isAscending,
                        sort.name
                    ) to TestSortUI.OnPublishDateDescending(
                        resourceHelper.extractStringResource(
                            SortStringResource.PublishDateDescending
                        ),
                        isDescending,
                        sort.name
                    )
                }

                TestOrderField.QUESTIONS -> {
                    TestSortUI.OnQuestionsCountAscending(
                        resourceHelper.extractStringResource(
                            SortStringResource.QuestionsCountAscending
                        ),
                        isAscending,
                        sort.name
                    ) to TestSortUI.OnQuestionsCountDescending(
                        resourceHelper.extractStringResource(
                            SortStringResource.QuestionsCountDescending
                        ),
                        isDescending,
                        sort.name
                    )
                }
            }
            testSorts.add(ascendingValue)
            testSorts.add(descendingValue)
        }
        return testSorts
    }
}
