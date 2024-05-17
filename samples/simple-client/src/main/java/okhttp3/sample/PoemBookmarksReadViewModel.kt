package okhttp3.sample

import com.google.gson.Gson
import java.util.concurrent.Executors
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException


@Suppress("SuspiciousIndentation")
fun main() {
  val viewModel = PoemBookmarksReadViewModel()
//  viewModel.getArticle()
//  viewModel.addArticle()

//  Executors.newCachedThreadPool().execute {
//  viewModel.getDetail() // 可以跑通,关闭代理

  viewModel.postOkhttpCache()
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
    val url = "https://jsonplaceholder.typicode.com/posts"
    val mediaType = "application/json".toMediaTypeOrNull()
//    val formBody = json.toRequestBody(mediaType)

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


  fun createInsecureTrustManager(): X509TrustManager = object : X509TrustManager {
    override fun checkClientTrusted(
      chain: Array<out java.security.cert.X509Certificate>?,
      authType: String?
    ) {
    }

    override fun checkServerTrusted(
      chain: Array<out java.security.cert.X509Certificate>?,
      authType: String?
    ) {
    }

    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
  }

  fun createInsecureSslSocketFactory(): javax.net.ssl.SSLSocketFactory {
    val trustManager = createInsecureTrustManager()
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, arrayOf(trustManager), java.security.SecureRandom())
    return sslContext.socketFactory
  }


  fun getDetail() {
    Executors.newCachedThreadPool().submit {
      val request: Request = Request.Builder()
        .url("http://172.34.83.1:8080/poi/detail/1")
        .build()

      val client = OkHttpClient.Builder()
        .sslSocketFactory(createInsecureSslSocketFactory(), createInsecureTrustManager())
        .hostnameVerifier { hostname, session -> true }
        .build()

      val response = client.newCall(request).execute()
      val string = response.body.string()
      println(string)
    }
  }


  fun postOkhttpCache() {
    Thread {
      val client = OkHttpClient.Builder()
        .sslSocketFactory(createInsecureSslSocketFactory(), createInsecureTrustManager())
        .hostnameVerifier { hostname, session -> true }
        .build()

//      val formBody = FormBody.Builder().add("id", "1").build()
      val myData ="{\"id\":\"1\",\"name\":\"john\"}"
      val mediaType = "application/json; charset=utf-8".toMediaType()
      val requestBody = myData.toRequestBody(mediaType)

      val request = Request.Builder()
        .url("http://172.34.83.1:8080/poi/detail/post")
        .post(requestBody)
        .build()

      val response1Body = client.newCall(request).execute().use {
//        if (!it.isSuccessful) throw java.io.IOException("Unexpected code $it")

        println("Response 1 response:          $it")
        println("Response 1 cache response:    ${it.cacheResponse}")
        println("Response 1 network response:  ${it.networkResponse}")
        return@use it.body.string()
      }
      println("Response 1 response:          $response1Body")


//      val response2Body = client.newCall(request).execute().use {
//        if (!it.isSuccessful) throw IOException("Unexpected code $it")
//
//        println("Response 2 response:          $it")
//        println("Response 2 cache response:    ${it.cacheResponse}")
//        println("Response 2 network response:  ${it.networkResponse}")
//        return@use it.body.string()
//      }
//      println("Response 2 response:          $response2Body")
//
//      println("Response 2 equals Response 1? " + (response1Body == response2Body))
    }.start()

  }
}



