package com.testeducation.converter.test

import com.testeducation.logic.model.test.CardTestStyle


fun getTestStyle(style: String) : CardTestStyle {
    return when (style) {
        "X" -> CardTestStyle.X
        "O" -> CardTestStyle.O
        "DOTTED" -> CardTestStyle.DOTTED
        else -> CardTestStyle.ELLIPSE
    }
}