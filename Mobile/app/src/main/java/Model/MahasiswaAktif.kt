package Model

import com.google.gson.annotations.SerializedName

class MahasiswaAktif{
    @SerializedName("nim")
    var nim: String? = null

    @field:SerializedName("password")
    var password: String? = null

    @field:SerializedName("nama")
    var nama: String? = null

    @field:SerializedName("jurusan")
    var jurusan: String? = null

    @field:SerializedName("program_studi")
    var program_studi: String? = null

    @field:SerializedName("angkatan")
    var angkatan: String? = null

    @field:SerializedName("email")
    var email: String? = null

    @field:SerializedName("password_confirmation")
    var password_confirmation: String? = null
}

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