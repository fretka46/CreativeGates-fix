package org.tamasoft.creativegate.util

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.io.IOException
import kotlin.jvm.Throws


object JsonFileUtil {

    val jsonMapper = ObjectMapper()

    @Throws(IOException::class)
    fun <T> read(file: File, cls: Class<T>): T {
        return jsonMapper.readValue(file, cls)
    }

    @Throws(IOException::class)
    fun <T> write(file: File, value: T) {
        return jsonMapper.writeValue(file, value)
    }
}