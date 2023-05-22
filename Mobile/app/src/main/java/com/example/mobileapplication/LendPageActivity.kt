package com.example.mobileapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import java.net.URL
import android.app.NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID
import android.os.Environment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class LendPageActivity : AppCompatActivity() {
    private lateinit var camera: ImageView
    private val cameraRequestId = 1222
    private val url =
        "https://dummypdf.readthedocs.io/en/latest/_downloads/04d2e9f24079f6f640f04ed2531c04d3/example1.pdf"
    private val fileName = "aaaaaaaaa.pdf"
    private val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lend_page)

        val varTvDownloadForm: TextView = findViewById(R.id.textViewDownloadForm)

        camera = findViewById(R.id.cameraOpen)
        if (ContextCompat.checkSelfPermission(
                applicationContext, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                cameraRequestId
            )

        // Camera Open
        camera.setOnClickListener(View.OnClickListener {
            val cameraInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraInt, cameraRequestId)
        })

        varTvDownloadForm.setOnClickListener(View.OnClickListener {
            downloadPdfFileAsync(url, fileName)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequestId) {
            val images: Bitmap = data?.extras?.get("data") as Bitmap
            camera.setImageBitmap(images)
        }
    }

    private fun downloadPdfFile(url: String): ByteArray {
        val inputStream = URL(url).openStream()
        return inputStream.readBytes()
    }

//    private fun savePdfToInternalStorage(byteArray: ByteArray, fileName: String, filePath: String) {
//        try {
//            val file = File(filePath)
//            val fileOutputStream = FileOutputStream(file)
//            fileOutputStream.write(byteArray)
//            fileOutputStream.close()
//            Log.d("try", "PDF file saved to internal storage at $filePath")
//        } catch (e: Exception) {
//            Log.e("catch", "Error saving PDF file to internal storage", e)
//        }
//    }

//    private fun savePdfToInternalStorage(byteArray: ByteArray, fileName: String, filePath: String) {
//        try {
//            val file = File(filePath)
//            val fileOutputStream = FileOutputStream(file)
//            openFileOutput(fileName, Context.MODE_PRIVATE).use { fileOutputStream ->
//                fileOutputStream.write(byteArray)
//                Log.d("try", "PDF file saved to internal storage as $fileName")
//                Log.d("try", "PDF file saved to external storage at ${file.absolutePath}")
//            }
//        } catch (e: Exception) {
//            Log.e("catch", "Error saving PDF file to internal storage", e)
//        }
//    }

    private fun savePdfToExternalStorage(byteArray: ByteArray, fileName: String) {
        try {
            val externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!externalDir.exists()) {
                externalDir.mkdirs()
            }
            val file = File(externalDir, fileName)
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(byteArray)
            fileOutputStream.close()
            Log.d("try", "PDF file saved to external storage at ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e("catch", "Error saving PDF file to internal storage", e)
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun showDownloadCompleteNotification(fileName: String) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create a notification channel for Android Oreo and higher
            val name = getString(R.string.notification_channel_name)
            val description = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(EXTRA_NOTIFICATION_CHANNEL_ID, name, importance).apply {
                    this.description = description
                }
            notificationManager.createNotificationChannel(channel)
        }

        // Create a notification to inform the user that the file has finished downloading
        val notification =
            NotificationCompat.Builder(this, NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text, fileName))
                .setSmallIcon(R.drawable.ic_download_complete)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun downloadPdfFileAsync(url: String, fileName: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val byteArray = downloadPdfFile(url)
            val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            if (externalFilesDir != null) {
                val filePath = "${externalFilesDir.absolutePath}/$fileName"
                savePdfToExternalStorage(byteArray, fileName)
                showDownloadCompleteNotification(fileName)
            }
        }
    }

//    private fun downloadPdfFileAsyncInternal(url: String, fileName: String) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val byteArray = downloadPdfFile(url)
//            savePdfToInternalStorage(byteArray, fileName)
//            showDownloadCompleteNotification(fileName)
//        }
//    }
}