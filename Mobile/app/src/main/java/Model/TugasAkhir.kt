package Model

import com.google.gson.annotations.SerializedName


class TugasAkhirResponse{
    @SerializedName("tugas_akhir_id")
    val tugas_akhir_id: String? = null

    @SerializedName("tugas_akhir")
    val tugas_akhir: TugasAkhir? = null
}

class TugasAkhir{
    @SerializedName("id")
    val id: String? = null

    @SerializedName("judul")
    val judul: String? = null
}

class DetailTugasAkhir{
    @SerializedName("id")
    val id: String? = null

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

    @SerializedName("filepath")
    val filepath: Filepath? = null

    @SerializedName("staf_perpus_pengunggah")
    val staf_perpus_pengunggah: String? = null

    @SerializedName("mahasiswa")
    val mahasiswa: ArrayList<Mahasiswa>? = null
}

class Filepath{
    @SerializedName("bab_1")
    val bab_1: String? = null

    @SerializedName("bab_2")
    val bab_2: String? = null

    @SerializedName("bab_3")
    val bab_3: String? = null

    @SerializedName("bab_4")
    val bab_4: String? = null

    @SerializedName("bab_5")
    val bab_5: String? = null

    @SerializedName("cover")
    val cover: String? = null

    @SerializedName("kelengkapan")
    val kelengkapan: String? = null
}

class Mahasiswa{
    @SerializedName("nim")
    val nim: String? = null

    @SerializedName("nama")
    val nama: String? = null

    @SerializedName("tugas_akhir_id")
    val tugas_akhir_id: String? = null
}