package com.example.mobileapplication

import Model.ReadPdfModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_read_pdf.*
import java.lang.Exception

class ReadPdfActivity : AppCompatActivity() {

    lateinit var viewModel: ReadPdfModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_pdf)
        initViewModel()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, object: ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ReadPdfModel(fileDir = filesDir) as T
            }
        }).get(ReadPdfModel::class.java)

        viewModel.isFileReadyObserver.observe(this, Observer <Boolean>{
            progressBar.visibility = View.GONE

            if(!it) {
                Toast.makeText(this@ReadPdfActivity, "Failed to Download PDF", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@ReadPdfActivity, "PDF Downloaded Successfully", Toast.LENGTH_SHORT).show()
                try {
                    pdfView.fromUri(FileProvider.getUriForFile(
                        applicationContext,
                        "com.example.mobileapplication.fileprovider",
                        viewModel.getPdfFileUri()))
                        .load()
                } catch (e: Exception) {
                    Toast.makeText(this@ReadPdfActivity, "Failed to Download PDF", Toast.LENGTH_SHORT).show()
                }
            }
        })
        val bundle: Bundle?= intent.extras
        val fileName = bundle?.getString("fileName").toString()
        viewModel.downloadPdfFile("http://hukor.kemkes.go.id/uploads/produk_hukum/PMK_No__11_Th_2019_ttg_Penanggulangan_Kusta.pdf")
    }
}