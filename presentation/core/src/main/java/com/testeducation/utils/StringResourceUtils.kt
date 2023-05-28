package com.testeducation.utils

import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource

fun StringResource.getString(resourceHelper: IResourceHelper) =
    resourceHelper.extractStringResource(this)