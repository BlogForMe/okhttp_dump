package okhttp3.sample

import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


fun main() {
  val viewModel = PoemBookmarksReadViewModel()
//  viewModel.getArticle()
  viewModel.addArticle()
}

class PoemBookmarksReadViewModel {
  val client = OkHttpClient()


  fun getArticle() {
//    Retrofit.Builder().baseUrl(jsonplaceURL).build().create(IApiStores::class.java).getArticle(2)
//      ?.enqueue(
//        object : Callback<ResponseBody?> {
//          override fun onResponse(
//            call: Call<ResponseBody?>,
//            response: Response<ResponseBody?>,
//          ) {
//            val body = response.body()?.string()
//
//            Log.i("PoemBookmarksReadViewModel", "onResponse: $body")
//          }
//
//          override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
//          }
//        },
//      )

    val request: Request = Request.Builder()
      .url("https://jsonplaceholder.typicode.com/posts/2")
      .build()

    client.newCall(request).execute().use { response ->
      println(response.body.string())
    }

  }


  val json = """
{
    "userId": "1",
    "title": "qui est esse",
    "body": "est rerum tempore vita",
    "id": 101
}
  """.trimIndent()
  fun addArticle() {

//    val formBody = FormBody.Builder()
//      .add("userId", "1")
//      .add("title", "article 2")
//      .add("body", "body article")
//      .build()
//
//    println(formBody)

    val client = OkHttpClient()
    val url = "https://jsonplaceholder.typicode.com/posts"
    val mediaType = "application/json".toMediaTypeOrNull()
    val requestBody = RequestBody.create(mediaType, json)

    val request = Request.Builder()
      .url(url)
      .post(requestBody)
      .build()

    val response = client.newCall(request).execute()
    val responseBody = response.body.string()

    // Handle the response
    if (response.isSuccessful) {
      // Successful request
      println("Response: $responseBody")
    } else {
      // Request failed
      println("Request failed: ${response.code}")
    }

  }

}


interface IApiStores {

//  @GET("posts/{articleId}")
//  fun getArticle(@retrofit2.http.Path("articleId") it: Int): Call<ResponseBody?>?
//
//
//  @POST("posts")
//  fun addArticle(@retrofit2.http.Body requestBody: RequestBody?): Call<ResponseBody?>?
}

