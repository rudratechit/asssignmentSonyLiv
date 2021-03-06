package com.sonyliv.sonylivasssignment.utils

import org.json.JSONException
import org.json.JSONObject
import java.io.FileInputStream
import java.lang.Exception
import java.util.Locale

class JsonLocalisation private constructor() {

    private var language = "en"
    private var jsonData = JSONObject()

    companion object {

        private var mInstance: JsonLocalisation? = null

        fun getInstance(): JsonLocalisation? {
            if (mInstance == null) {
                mInstance = JsonLocalisation()
                mInstance!!.setLanguage(Locale.getDefault().language)
            }
            return mInstance
        }
    }

    fun setLanguage(language: String) {
        this.language = language
    }

    fun getLanguage(): String {
        return language
    }

    fun loadFromData(jsonData: JSONObject) {
        this.jsonData = jsonData
    }

    fun loadFromData(jsonString: String) {
        try {
            this.loadFromData(JSONObject(jsonString))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun loadFromFileName(fileName: String) {
        val fis: FileInputStream
        val fileContent: StringBuffer
        try {
            fis = globalContext.openFileInput(fileName)
            fileContent = StringBuffer("")
            val buffer = ByteArray(1024)
            var n: Int
            while (fis.read(buffer).also { n = it } != -1) {
                fileContent.append(String(buffer, 0, n))
            }
            this.loadFromData(fileContent.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stringForKey(key: String): String {
        var localData: JSONObject? = null
        try {
            localData = jsonData.getJSONObject(language)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        var value = key // key is the default value returned if key is not found in json
        if (localData != null) {
            if (localData.has(key)) {
                try {
                    value = localData.getString(key)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        return value
    }
}
