package okhttp.android.testapp

import android.content.Context
import android.util.Log
import java.io.File
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpRequest {

  val TAG = "OkHttpRequest"
  fun testCache(context: Context) {

    val cacheFile = File(context.externalCacheDirs.toString(), "okhttp_cache")
    val cache = Cache(cacheFile, 10 * 1024 * 1024)

    val client = OkHttpClient.Builder().cache(cache).build()

    val request = Request.Builder().url("https://publicobject.com/helloworld.txt").build()
    val call1 = client.newCall(request)
    val response1 = call1.execute()

    try {
      Log.i(TAG, "testCache: response1 : " + response1.body.string())
      Log.i(TAG, "testCache: response1 cache : " + response1.cacheResponse)
      Log.i(TAG, "testCache: response1  network: " + response1.networkResponse)


      val call2 = client.newCall(request)
      val response2 = call2.execute()
      Log.i(TAG, "testCache: response2 : " + response2.body.string())
      Log.i(TAG, "testCache: response2 cache : " + response2.cacheResponse)
      Log.i(TAG, "testCache: response2  network: " + response2.networkResponse)
    } catch (e: Exception) {
      e.printStackTrace()
    }


  }
}

