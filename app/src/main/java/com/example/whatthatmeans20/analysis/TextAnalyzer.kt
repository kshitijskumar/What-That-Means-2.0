package com.example.whatthatmeans20.analysis

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.whatthatmeans20.utils.Injector
import com.example.whatthatmeans20.utils.Resources
import com.example.whatthatmeans20.utils.Resources.Error
import com.example.whatthatmeans20.utils.Resources.Success
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognizer
import java.util.concurrent.TimeUnit

class TextAnalyzer(
    private val recognizer: TextRecognizer = Injector.getInjector().providesRecognizer(),
    private val listener: (Resources<Text>) -> Unit
) : ImageAnalysis.Analyzer {

    private var lastAnalyzedTime = 0L

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val currentTimeStamp = System.currentTimeMillis()
        if(currentTimeStamp - lastAnalyzedTime >= TimeUnit.SECONDS.toMillis(5L)) {
            listener.invoke(Resources.Loading)
            val mediaImage = image.image
            if(mediaImage != null) {
                val analysisImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
                extractTextFromImages(analysisImage) {
                    image.close()
                }
            }
            lastAnalyzedTime = currentTimeStamp
        }else {
            image.close()
        }

    }

    private fun extractTextFromImages(image: InputImage, onSuccess: () -> Unit) {
        recognizer.process(image)
            .addOnSuccessListener {
                listener.invoke(Success(it))
                onSuccess.invoke()
            }
            .addOnFailureListener {
                Log.d("Analysis", "Failure is: ${it.message}")
                listener.invoke(Error(it.message ?: "Something went wrong"))
            }
    }
}