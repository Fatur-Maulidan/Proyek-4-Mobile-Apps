package retrofit

import retrofit2.http.GET

interface ApiEndpoint {
    @GET("posts")
    fun getPosts(): Call<ArrayList<PostResponse>>
}