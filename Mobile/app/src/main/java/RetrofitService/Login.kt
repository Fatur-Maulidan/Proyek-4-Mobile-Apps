package RetrofitService

import KeyStore.CryptoManager
import KeyStore.Preferences

class Login {
//    val preferences = Preferences()
//    val cryptoManager = CryptoManager()
//    fun loginAuth(mahasiswaAktif: ResponseMahasiswaAktif, context: Context, file: FileOutputStream){
//        val customLayout = CustomLayout(context)
//        ApiService.endPoint().postLogin(mahasiswaAktif).enqueue(object : Callback<ResponseMahasiswaAktif>{
//            override fun onResponse(call: Call<ResponseMahasiswaAktif>, response: Response<ResponseMahasiswaAktif>) {
//                val mahasiswaAktif = response.body()
//                if(mahasiswaAktif?.message.equals("Berhasil login.")){
//                    preferences.setToken(context, cryptoManager.encrypt(bytes = mahasiswaAktif?.token?.encodeToByteArray()!!, outputStream = file).decodeToString())
//                } else {
//                    customLayout.showCustomToast("Error Ketika Login",R.layout.toast_custom_layout_failed)
//                }
//            }
//            override fun onFailure(call: Call<ResponseMahasiswaAktif>, t: Throwable) {
//                customLayout.showCustomToast(t.localizedMessage,R.layout.toast_custom_layout_failed)
//            }
//        })
//    }
}