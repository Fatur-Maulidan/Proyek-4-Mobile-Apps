package Model

import com.google.gson.annotations.SerializedName

class PostResponse {
    val id: Int = 0
    val title: String? = null
    @SerializedName("body")
    val text: String? = null
}