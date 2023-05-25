package Model

import com.google.gson.annotations.SerializedName

// Class model ini digunakan untuk melakukan proses authentikasi

class MahasiswaAktif{
    @SerializedName("nim")
    var nim: String? = null

    @field:SerializedName("password")
    var password: String? = null

    @field:SerializedName("nama")
    var nama: String? = null

    @field:SerializedName("email")
    var email: String? = null

    @field:SerializedName("password_confirmation")
    var password_confirmation: String? = null
}


// Class model ini digunakan ketika proses authentikasi berhasil dan akan memberikan
// responsenya berupa token
class ResponseMahasiswaAktif {
    @SerializedName("message")
    var message: String? = null

    @SerializedName("mahasiswa")
    var mahasiswa: MahasiswaAktif? = null

    @SerializedName("token")
    var token: String? = null
}

class ResponseMessage {
    @SerializedName("message")
    var message: String? = null
}