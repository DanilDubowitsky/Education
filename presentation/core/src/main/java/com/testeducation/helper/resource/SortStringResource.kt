package com.testeducation.helper.resource

sealed interface SortStringResource : StringResource {
    object NameDescending : SortStringResource
    object NameAscending : SortStringResource
    object CreationDateDescending : SortStringResource
    object CreationDateAscending : SortStringResource
    object QuestionsCountDescending : SortStringResource
    object QuestionsCountAscending : SortStringResource
    object PublishDateDescending : SortStringResource
    object PublishDateAscending : SortStringResource
}
