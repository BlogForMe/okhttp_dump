package okhttp3.sample

import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException


fun main() {
  val viewModel = PoemBookmarksReadViewModel()
  viewModel.getArticle()
//  viewModel.addArticle()
}

class PoemBookmarksReadViewModel {

  val client = OkHttpClient()

  fun getArticle() {
    val request: Request = Request.Builder()
      .url("https://jsonplaceholder.typicode.com/posts/2")
      .build()

//    client.newCall(request).execute().use { response ->
//      println(response.body.string())
//    }

    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(call: Call, e: IOException) {
      }

      override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful) {
          // Successful request
          println("Response: ${response.body.string()}")
        } else {
          // Request failed
          println("Request failed: ${response.code}")
        }
      }
    })
  }

//      println(response.body.string())


  val json = """
{
    "userId": "1",
    "title": "qui est esse",
    "body": "est rerum tempore vita",
    "id": 101
}
  """.trimIndent()

  fun addArticle() {
    val formBody = FormBody.Builder()
      .add("userId", "1")
      .add("title", "article 2")
      .add("body", "body article")
      .build()
//    println(formBody)

    val url = "https://jsonplaceholder.typicode.com/posts"
    val mediaType = "application/json".toMediaTypeOrNull()
    val requestBody = RequestBody.create(mediaType, json)

    val request = Request.Builder()
      .url(url)
      .post(formBody)
      .build()

    val response = client.newCall(request).enqueue(
      object : Callback {
        override fun onFailure(call: Call, e: IOException) {
        }

        override fun onResponse(call: Call, response: Response) {
          if (response.isSuccessful) {
            // Successful request
            println("Response: ${response.body.string()}")
          } else {
            // Request failed
            println("Request failed: ${response.code}")
          }
        }

      }
    )

  }
}



