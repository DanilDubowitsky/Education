package com.testeducation.ui.helper.extractor

import com.testeducation.helper.resource.CodeStringResource
import com.testeducation.ui.R

fun CodeStringResource.extractResourceId() = when (this) {
    CodeStringResource.CodeError -> R.string.code_enter_code_invalid_error
}
