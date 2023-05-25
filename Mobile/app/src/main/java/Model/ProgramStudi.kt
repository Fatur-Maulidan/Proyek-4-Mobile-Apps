package Model

import com.google.gson.annotations.SerializedName

// Class model ini digunakan untuk menerima response dari API

class ProgramStudi {
    @SerializedName("nama")
    var nama: String? = null

    @SerializedName("diploma")
    var diploma: String? = null

    @SerializedName("nomor")
    var nomor: String? = null
}