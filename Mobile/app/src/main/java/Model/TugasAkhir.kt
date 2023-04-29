package Model

import com.google.gson.annotations.SerializedName

class TugasAkhir{
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("judul")
    val judul: String? = null

    @SerializedName("first_mahasiswa")
    val first_mahasiswa: String? = null
}

class DetailTugasAkhir{
    @SerializedName("id")
    val id: Int = 0

    @SerializedName("judul")
    val judul: String? = null

    @SerializedName("tahun")
    val tahun: String? = null

    @SerializedName("kata_kunci")
    val kata_kunci: String? = null

    @SerializedName("kontributor_1")
    val kontributor_1: String? = null

    @SerializedName("kontributor_2")
    val kontributor_2: String? = null

    @SerializedName("kontributor_3")
    val kontributor_3: String? = null

    @SerializedName("mahasiswa")
    val mahasiswa = Array(3) {""}
}