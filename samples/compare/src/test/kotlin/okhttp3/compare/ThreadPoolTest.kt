package okhttp3.compare

import java.io.IOException
import java.util.concurrent.TimeUnit
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test

class ThreadPoolTest {

  @Test
  fun threadPoolTest() {
    mainThread()
  }


  fun mainThread() {
    // Configure the connection pool with custom settings
    val connectionPool = ConnectionPool(
      maxIdleConnections = 5,
      keepAliveDuration = 5,
      timeUnit = TimeUnit.MINUTES
    )

    // Create an OkHttpClient with the custom connection pool
    val client = OkHttpClient.Builder()
      .connectionPool(connectionPool)
      .build()

    // Define a URL to send requests to
    val url = "https://jsonplaceholder.typicode.com/posts/1"

    // Create and execute multiple requests to demonstrate connection reuse
    for (i in 1..5) {
      val request = Request.Builder()
        .url(url)
        .build()

      try {
        client.newCall(request).execute().use { response ->
          if (response.isSuccessful) {
            println("Response $i: ${response.body?.string()}")
          } else {
            println("Request $i failed: $response")
          }
        }
      } catch (e: IOException) {
        println("Request $i failed: ${e.message}")
      }
    }

    // Optionally, print the connection pool status
    println("Connection Pool: $connectionPool")
  }

}
