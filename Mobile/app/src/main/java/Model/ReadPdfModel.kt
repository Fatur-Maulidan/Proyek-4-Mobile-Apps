package Model

import Retrofit.ApiEndpoint
import Retrofit.ApiService
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import kotlin.concurrent.thread

class ReadPdfModel(val fileDir: File) : ViewModel() {
    private var pdfFileName: File
    private var dirPath: String
    private var fileName: String
    var isFileReadyObserver = MutableLiveData<Boolean>()

    init {
        dirPath = "${fileDir}/cert/pdfFiles"
        val dirFile = File(dirPath)
        if(!dirFile.exists()){
            dirFile.mkdirs()
        }
        Log.d("=====dir", dirFile.toString())
        fileName = "DemoGrahps.pdf"
        val file = "${dirPath}/${fileName}"
        pdfFileName = File(file)
        if(pdfFileName.exists()){
            pdfFileName.delete()
        }
    }

    fun getPdfFileUri(): File = pdfFileName

    fun downloadPdfFile(pdfUrl: String) {
        thread {
            val apiService = ApiService().endPoint().create(ApiEndpoint::class.java)
            apiService.downloadPdfFile(pdfUrl).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("======","======Response : "+response)
                    Log.d("======","======Response When Success : "+response.isSuccessful)
                    if (response.isSuccessful) {
                        val result = response.body()?.byteStream()
                        result?.let {
                            writeToFile(it)
                        } ?: kotlin.run {
                            isFileReadyObserver.postValue(false)
                        }
                    } else {
                        isFileReadyObserver.postValue(false)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    isFileReadyObserver.postValue(false)
                }

            })
        }
    }

    private fun writeToFile(inputStream: InputStream){
        try {
            Log.d("err1", "Error : ")
            val fileReader = ByteArray(4096)
            var fileSizeDownloaded = 0
            val fos: OutputStream = FileOutputStream(pdfFileName)
            do {
                val read = inputStream.read(fileReader)
                if (read != -1) {
                    fos.write(fileReader, 0, read)
                    fileSizeDownloaded += read
                }
            } while (read != -1)
            fos.flush()
            fos.close()
            isFileReadyObserver.postValue(true)
        } catch (e:IOException) {
            Log.d("err1", "Error"+e)
            isFileReadyObserver.postValue(false)
        }
    }
}