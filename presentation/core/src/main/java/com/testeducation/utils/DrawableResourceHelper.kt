package com.testeducation.utils

import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.IResourceHelper

fun DrawableResource.getDrawable(resourceHelper: IResourceHelper) =
    resourceHelper.extractDrawableResource(this)