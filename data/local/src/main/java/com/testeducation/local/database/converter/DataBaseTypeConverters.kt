package com.testeducation.local.database.converter

import androidx.room.TypeConverter

class DataBaseTypeConverters {

    @TypeConverter
    fun collectionToStringConverter(value: Collection<String>): String {
        return value.joinToString(separator = LIST_SEPARATOR)
    }

    @TypeConverter
    fun stringToCollectionConverter(value: String): List<String> {
        return value.split(LIST_SEPARATOR)
    }

    private companion object {
        const val LIST_SEPARATOR = "<|>"
    }
}
