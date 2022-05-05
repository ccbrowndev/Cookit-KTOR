package com.cookit.service

import androidx.room.TypeConverter
import java.util.*

class StringMapConverter {
    @TypeConverter
    fun fromStringMap(value: Map<String, String>): String {
        val sortedMap = TreeMap(value)
        return sortedMap.keys.joinToString(separator = ",").plus("<divider>")
            .plus(sortedMap.values.joinToString(separator = ","))
    }

    @TypeConverter
    fun toStringMap(value: String): Map<String, String> {
        return value.split("<divider>").run {
            val keys = getOrNull(0)?.split(",")?.map { it }
            val values = getOrNull(1)?.split(",")?.map { it }

            val res = hashMapOf<String, String>()
            keys?.forEachIndexed { index, s ->
                res[s] = values?.getOrNull(index) ?: ""
            }
            res
        }
    }
}