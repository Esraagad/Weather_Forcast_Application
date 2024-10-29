package com.example.weatherforecastapplication.other

import android.content.Context
import java.io.IOException


fun readJsonFromAssets(context: Context, fileName: String): String? {
    var json: String? = null
    try {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}
