package okhttp.android.testapp

import android.content.Context
import android.util.Log
import java.io.File
import java.io.IOException
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpRequest {

  val TAG = "OkHttpRequest"
  fun testCache(context: Context) {

    Thread {
      val client: OkHttpClient = OkHttpClient.Builder()
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
        return@use it.body?.string()
      }
      println("Response 1 response:          $response1Body")


      val response2Body = client.newCall(request).execute().use {
        if (!it.isSuccessful) throw IOException("Unexpected code $it")

        println("Response 2 response:          $it")
        println("Response 2 cache response:    ${it.cacheResponse}")
        println("Response 2 network response:  ${it.networkResponse}")
        return@use it.body?.string()
      }
      println("Response 2 response:          $response2Body")

      println("Response 2 equals Response 1? " + (response1Body == response2Body))
    }.start()

  }
}

