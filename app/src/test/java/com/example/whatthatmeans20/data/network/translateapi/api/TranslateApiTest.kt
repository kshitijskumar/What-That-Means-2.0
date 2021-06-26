package com.example.whatthatmeans20.data.network.translateapi.api

import android.util.Log
import com.example.whatthatmeans20.data.network.translateapi.model.TranslateTextModel
import com.example.whatthatmeans20.utils.Injector
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TranslateApiTest {

    private lateinit var api: TranslateApi

    @Before
    fun setup() {
        api = Injector.getInjector().providesTranslateApi()
    }

    @Test
    fun translateWordToEnglish_onSuccess_returnsEnglishTranslation() = runBlocking {
        val resp = api.translateWordToEnglish("en", listOf(TranslateTextModel("Bonjour")))

        Assert.assertNotNull(resp)
        Assert.assertEquals(200, resp.code())
    }
}