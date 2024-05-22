package okhttp.android.testapp

import android.content.Context
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class OkHttpRequest {

  val TAG = "OkHttpRequest"
  fun testOfficeCache(context: Context) {

    Thread {
      val client: OkHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(createInsecureSslSocketFactory(), createInsecureTrustManager())
        .hostnameVerifier { hostname, session -> true }
        .cache(
          Cache(
            directory = context.cacheDir,
            maxSize = 10L * 1024L * 1024L // 10 MiB
          )
        )
        .build()

      val request = Request.Builder()
        .url("https://publicobject.com/helloworld.txt")
        .build()

      val response1Body = client.newCall(request).execute().use {
        if (!it.isSuccessful) throw IOException("Unexpected code $it")

        println("Response 1 response:          $it")
        println("Response 1 cache response:    ${it.cacheResponse}")
        println("Response 1 network response:  ${it.networkResponse}")
        return@use it.body.string()
      }
      println("Response 1 response:          $response1Body")


      val response2Body = client.newCall(request).execute().use {
        if (!it.isSuccessful) throw IOException("Unexpected code $it")

        println("Response 2 response:          $it")
        println("Response 2 cache response:    ${it.cacheResponse}")
        println("Response 2 network response:  ${it.networkResponse}")
        return@use it.body.string()
      }
      println("Response 2 response:          $response2Body")

      println("Response 2 equals Response 1? " + (response1Body == response2Body))
    }.start()
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


  fun postOkhttpCache(context: Context) {
    Thread {
      val client = OkHttpClient.Builder()
        .sslSocketFactory(createInsecureSslSocketFactory(), createInsecureTrustManager())
        .hostnameVerifier { hostname, session -> true }
        .cache(
          Cache(
            directory = context.cacheDir,
            maxSize = 10L * 1024L * 1024L // 10 MiB
          )
        )
        .build()

//      val formBody = FormBody.Builder().add("id", "1").build()
      val myData = "{\"id\":\"1\",\"name\":\"john\"}"
      val mediaType = "application/json; charset=utf-8".toMediaType()
      val requestBody = myData.toRequestBody(mediaType)

      val request = Request.Builder()
//        .url("http://172.34.83.1:8080/poi/detail/post")
        .url("http://10.0.0.5:8080/poi/detail/post")  // run this with emulator
        .post(requestBody)
        .cacheControl(CacheControl.Builder().maxAge(60, TimeUnit.SECONDS).build())
        .build()

      val response1Body = client.newCall(request).execute().use {
        if (!it.isSuccessful) throw java.io.IOException("Unexpected code $it")
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
//      println("Response 2 equals Response 1? " + (response1Body == response2Body))


    }.start()
  }


  fun testGetCache(context: Context) {
    Thread {
      val client: OkHttpClient = OkHttpClient.Builder()
        .sslSocketFactory(createInsecureSslSocketFactory(), createInsecureTrustManager())
        .hostnameVerifier { hostname, session -> true }
        .cache(
          Cache(
            directory = context.cacheDir,
            maxSize = 10L * 1024L * 1024L // 10 MiB
          )
        )
        .build()

      val request = Request.Builder()
        .url("http://172.34.83.1:8080/poi/detail/1")
        .build()

      val response1Body = client.newCall(request).execute().use {
        if (!it.isSuccessful) throw IOException("Unexpected code $it")

        println("Response 1 response:          $it")
        println("Response 1 cache response:    ${it.cacheResponse}")
        println("Response 1 network response:  ${it.networkResponse}")
        return@use it.body.string()
      }
//      println("Response 1 response:          $response1Body")


      val response2Body = client.newCall(request).execute().use {
        if (!it.isSuccessful) throw IOException("Unexpected code $it")

        println("Response 2 response:          $it")
        println("Response 2 cache response:    ${it.cacheResponse}")
        println("Response 2 network response:  ${it.networkResponse}")
        return@use it.body.string()
      }
//      println("Response 2 response:          $response2Body")

      println("Response 2 equals Response 1? " + (response1Body == response2Body))
    }.start()
  }

}

