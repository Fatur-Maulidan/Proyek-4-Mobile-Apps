package com.example.mobileapplication

import CustomClass.LoadingDialog
import Retrofit.ApiEndpoint
import Retrofit.ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CompletableDeferred
import KeyStore.Preferences
import Model.DetailTugasAkhir
import Model.TugasAkhir
import RecyclerViewData.PostAdapter
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class FinalTaskPageActivity : AppCompatActivity() {
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var varTvTitle: TextView
    private lateinit var varTvAuthor: TextView
    private lateinit var varTvDescription: TextView
    private lateinit var varTvFile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_task_page)

        // Memanggil class preferences
        val preferences = Preferences()

        varTvTitle = findViewById(R.id.tvTitle)
        varTvAuthor = findViewById(R.id.textViewAuthor)
        varTvDescription = findViewById(R.id.textViewDescription)
        varTvFile = findViewById(R.id.textViewFile)
        val varTvText : TextView = findViewById(R.id.tvText)

        // Mengambil data dari intent sebelumnya
        val varGetId: String
        val bundle: Bundle?= intent.extras
        varTvTitle.text = bundle?.getString("judul")
        varGetId = bundle?.getString("id").toString()

        varTvText.text = preferences.getJurusan(applicationContext)

        loadingDialog = LoadingDialog(this)

        loadingDialog.startLoadingDialog()
        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            lifecycleScope.launch {
                getTugasAkhirFromDatabase(varGetId, preferences)
                loadingDialog.dismissDialog()
            }
        }, 5000)
    }

    private fun getTugasAkhirFromDatabase(id: String, preferences: Preferences) {
        val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
        apiService.getTugasAkhirById("Bearer " + preferences.getToken(applicationContext), id)
            .enqueue(object : Callback<DetailTugasAkhir>{
            override fun onResponse(
                call: Call<DetailTugasAkhir>,
                response: Response<DetailTugasAkhir>
            ) {
                var dataTugasAkhir = response.body()
                dataTugasAkhir?.let { dataToLayout(it) }
            }

            override fun onFailure(call: Call<DetailTugasAkhir>, t: Throwable) {
                Log.d("error",t.localizedMessage)
            }
        })
    }

    private fun dataToLayout(dataTugasAkhir: DetailTugasAkhir){
        var elemenMahasiswaIterasi: String? = ""
        for (item in 0 until dataTugasAkhir?.mahasiswa!!.size){
            elemenMahasiswaIterasi += dataTugasAkhir?.mahasiswa!![item]?.nama + " (" +
                    dataTugasAkhir?.mahasiswa!![item]?.nim + ") \n" + ""
        }
        varTvAuthor.text = "Mahasiswa : \n" +
                elemenMahasiswaIterasi +
                "Tahun : " + dataTugasAkhir?.tahun + "\n" +
                "Kata Kunci : " + dataTugasAkhir?.kata_kunci

        varTvDescription.text = "Deskripsi : \n" +
                "Kontributor 1 : " + dataTugasAkhir.kontributor_1 + "\n" +
                "Kontributor 2 : " + dataTugasAkhir.kontributor_2 + "\n" +
                "Kontributor 3 : " + dataTugasAkhir.kontributor_3 + "\n" +
                "Penggunggah : " + dataTugasAkhir.staf_perpus_pengunggah

        varTvFile.text = "File : \n" +
                "Bab 1 : " + removeSubString(dataTugasAkhir.filepath?.bab_1.toString(),dataTugasAkhir.id.toString()) + "\n" +
                "Bab 2 : " + removeSubString(dataTugasAkhir.filepath?.bab_2.toString(),dataTugasAkhir.id.toString()) + "\n" +
                "Bab 3 : " + removeSubString(dataTugasAkhir.filepath?.bab_3.toString(),dataTugasAkhir.id.toString()) + "\n" +
                "Bab 4 : " + removeSubString(dataTugasAkhir.filepath?.bab_4.toString(),dataTugasAkhir.id.toString()) + "\n" +
                "Bab 5 : " + removeSubString(dataTugasAkhir.filepath?.bab_5.toString(),dataTugasAkhir.id.toString()) + "\n" +
                "Cover : " + removeSubString(dataTugasAkhir.filepath?.cover.toString(),dataTugasAkhir.id.toString()) + "\n" +
                "Kelengkapan : " + removeSubString(dataTugasAkhir.filepath?.kelengkapan.toString(),dataTugasAkhir.id.toString())

        varTvFile.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ReadPdfActivity::class.java)
            intent.putExtra("idTugasAkhir", dataTugasAkhir?.id)
            intent.putExtra("fileName", dataTugasAkhir?.filepath?.bab_1)
            startActivity(intent)
        })
    }

    private fun removeSubString(string: String, id: String): String{
        return string.replace(id + "/", "")?.substringBefore(".")
    }

}