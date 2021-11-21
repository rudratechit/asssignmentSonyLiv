package com.sonyliv.sonylivasssignment.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.sonyliv.sonylivasssignment.R
import com.sonyliv.sonylivasssignment.model.DownloadResult
import com.sonyliv.sonylivasssignment.model.LocalisationFileData
import com.sonyliv.sonylivasssignment.utils.JsonLocalisation
import com.sonyliv.sonylivasssignment.utils.downloadFile
import io.ktor.client.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val jsonString = "{\"en\":{\"hello_world\":\"Hello World English local\"},\"fr\":{\"hello_world\":\"Hello World French local\"}}"
    val ktor: HttpClient by inject()
    companion object {
        val fileData = arrayOf(
            LocalisationFileData(
                Locale.GERMAN.language,
                "Localisation_de.txt",
                "https://www.jiocloud.com/share/?s=Y8hPwQvmc5ygRIaZd4dDdBXCOXE_uP1TIZA_h4t3Wh4jqE"
            ),
            LocalisationFileData(
                Locale.KOREA.language,
                "Localisation_ko.txt",
                "https://www.jiocloud.com/share/?s=cX7xwrsMeBWXuYncOfDN0EnxKgsWYW95YNz9ivjQJ7QOe5"
            ),
            LocalisationFileData(
                Locale.US.language,
                "Localisation_en.txt",
                "https://www.jiocloud.com/share/?s=VOgf-iF3YihFXssvC63kit_XXxhRISJojbcNWeG9y7o0IO"
            ),
            LocalisationFileData(
                Locale.FRENCH.language,
                "Localisation_fr.txt",
                "https://www.jiocloud.com/share/?s=WZLqWDgS2HMrJB8db7xhr_4qIYAZUzKUeGq8ONckA-8Oe5"
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnKoreanLocale.setOnClickListener(this)
        btnEnglishLocale.setOnClickListener(this)
        btnFrenchLocale.setOnClickListener(this)
        btnMaxicanLocale.setOnClickListener(this)

        getLocalizedString(Locale.US.language)
    }

    private fun getLocalizedString(language: String) {
        val key = "hello_world"
        val instance = JsonLocalisation.getInstance()!!
        instance.setLanguage(language)
        instance.loadFromData(jsonString)
        val localizedName: String = instance.stringForKey(key)
        val etView = findViewById<View>(R.id.etTranslationText) as EditText
        etView.setText("Localized Name : $localizedName")
    }

    private fun getLocalizedString(localisationFileData: LocalisationFileData) {
        val key = "hello_world"
        val instance = JsonLocalisation.getInstance()!!
        instance.setLanguage(localisationFileData.language)
        instance.loadFromFileName(localisationFileData.title)
        val localizedName: String = instance.stringForKey(key)
        val etView = findViewById<View>(R.id.etTranslationText) as EditText
        etView.setText("Localized Name : $localizedName")
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnKoreanLocale -> {
                checkAndDownloadLocalisedFile(view, fileData[1])
            }
            R.id.btnEnglishLocale -> {
                checkAndDownloadLocalisedFile(view, fileData[2])
            }
            R.id.btnFrenchLocale -> {
                checkAndDownloadLocalisedFile(view, fileData[3])
            }
            R.id.btnMaxicanLocale -> {
                checkAndDownloadLocalisedFile(view, fileData[0])
            }
        }
    }

    private fun checkAndDownloadLocalisedFile(
        view: View,
        localisationFileData: LocalisationFileData,
    ) {
        when {
            localisationFileData.isDownloading -> {
                // Do nothing
            }
            localisationFileData.file.exists() -> getLocalizedString(localisationFileData.language)
            else -> {
                try {
                    downloadWithFlow(view, localisationFileData)
                } catch (e: Exception) {
                    // generic error while downloading
                }
            }
        }
    }

    private fun downloadWithFlow(view: View, localisationFileData: LocalisationFileData) {
        CoroutineScope(Dispatchers.IO).launch {
            ktor.downloadFile(localisationFileData.file, localisationFileData.url).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is DownloadResult.Success -> {
                            (view as CircularProgressButton).revertAnimation()
                            getLocalizedString(localisationFileData.language)
                        }
                        is DownloadResult.Error -> {
                            (view as CircularProgressButton).revertAnimation()
                            Toast.makeText(this@MainActivity, "Error while downloading ${localisationFileData.title} try again", Toast.LENGTH_LONG).show()
                        }
                        is DownloadResult.Progress -> {
                            (view as CircularProgressButton).setProgress(it.progress.toFloat())
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        btnEnglishLocale.dispose()
        btnKoreanLocale.dispose()
        btnFrenchLocale.dispose()
        btnMaxicanLocale.dispose()
    }
}
