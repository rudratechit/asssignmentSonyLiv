package com.sonyliv.sonylivasssignment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.sonyliv.sonylivasssignment.utils.JsonLocalisation
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val jsonString = "{\"en\":{\"hello_world\":\"Hello World English\"},\"fr\":{\"hello_world\":\"Hello World French\"},\"ko\":{\"hello_world\":\"Hello World Korean\"},\"de\":{\"hello_world\":\"Hello World German\"}}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnHindiLocale.setOnClickListener(this)
        btnEnglishLocale.setOnClickListener(this)
        btnFrenchLocale.setOnClickListener(this)
        btnMaxicanLocale.setOnClickListener(this)

        getLocalizedString(Locale.US)
    }

    private fun getLocalizedString(locale: Locale) {
        val key = "hello_world"
        val instance = JsonLocalisation.getInstance()!!
        instance.setLanguage(locale.language)
        instance.loadFromData(this.jsonString)
        val localizedName: String = instance.stringForKey(key)
        Log.d("JsonLocaliztion", localizedName)

        val etView = findViewById<View>(R.id.etTranslationText) as EditText
        etView.setText("Localized Name : $localizedName")
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.btnHindiLocale -> {
                getLocalizedString(Locale.KOREA)
            }
            R.id.btnEnglishLocale -> {
                getLocalizedString(Locale.US)
            }
            R.id.btnFrenchLocale -> {
                getLocalizedString(Locale.FRENCH)
            }
            R.id.btnMaxicanLocale -> {
                getLocalizedString(Locale.GERMAN)
            }
        }
    }
}
