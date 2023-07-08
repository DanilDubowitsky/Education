package com.testeducation.utils

import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource

fun ColorResource.getColor(resourceHelper: IResourceHelper) =
    resourceHelper.extractColorResource(this)