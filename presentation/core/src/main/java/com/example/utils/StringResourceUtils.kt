package com.example.utils

import com.example.helper.resource.IResourceHelper
import com.example.helper.resource.StringResource

fun StringResource.getString(resourceHelper: IResourceHelper) =
    resourceHelper.extractStringResource(this)