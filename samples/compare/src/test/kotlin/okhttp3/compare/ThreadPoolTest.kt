package okhttp3.compare

import java.util.concurrent.TimeUnit
import okhttp3.Call
import okhttp3.Callback
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.junit.Test


fun main() {
  ThreadPoolTest().mainThread()

}

class ThreadPoolTest {

//  @Test
//  fun threadPoolTest() {
//    mainThread()
//  }


  fun mainThread() {
    // Configure the connection pool with custom settings
    val connectionPool = ConnectionPool(
      maxIdleConnections = 2,
      keepAliveDuration = 120,
      timeUnit = TimeUnit.MINUTES
    )


    println("thread ${Thread.currentThread()}")

    // Create an OkHttpClient with the custom connection pool
    val client = OkHttpClient.Builder()
      .connectionPool(connectionPool)
      .build()

    // Define a URL to send requests to

    val arrayOf = arrayOf(
      "https://jsonplaceholder.typicode.com/posts/1",
      "https://publicobject.com/helloworld.txt",
      "https://dog.ceo/api/breeds/image/random",
      "https://api.imgur.com/3/image",
      "https://api.github.com/gists/c2a7c39532239ff261be",
      "http://httpbin.org/delay/1",
      "http://api.open-notify.org/iss-now.json"
    )
    // Create and execute multiple requests to demonstrate connection reuse
    for (url in arrayOf) {
      val request = Request.Builder()
        .url(url)
        .build()

      client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: okio.IOException) {
//          println("Request $i failed: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
          println("thread ${Thread.currentThread()}")
//          println("Response : ${response.body.string()}")
        }
      })
    }


//      try {
//        client.newCall(request).execute().use { response ->
//          if (response.isSuccessful) {
////            println("Response $i: ${response.body?.string()}")
//          } else {
//            println("Request $i failed: $response")
//          }
//        }
//      } catch (e: IOException) {
//        println("Request $i failed: ${e.message}")
//      }
//    }

    // Optionally, print the connection pool status
    println("Connection Pool: $connectionPool")
  }

}
