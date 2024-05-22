package okhttp.android.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import java.io.IOException
import okhttp.android.testapp.databinding.ActivityOkhttpBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.net.ssl.*
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class OkhttpActivity : ComponentActivity() {
  val client by lazy { disableCertificateVerification() }
  val okHttpRequest = OkHttpRequest()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityOkhttpBinding.inflate(layoutInflater)
    setContentView(binding.root)


//    binding.btGet.setOnClickListener {
//      getRequest()
//    }
//
//    binding.btAddArticle.setOnClickListener {
//      addArticle()
//    }

  }

  fun getRequest() {
    val request: Request = Request.Builder()
      .url("https://api.github.com/repos/square/okhttp/contributors")
      .build()

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


  fun disableCertificateVerification(): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
      override fun checkClientTrusted(
        chain: Array<out java.security.cert.X509Certificate>?,
        authType: String?,
      ) {
      }

      override fun checkServerTrusted(
        chain: Array<out java.security.cert.X509Certificate>?,
        authType: String?,
      ) {
      }

      override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
        return arrayOf()
      }
    })

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    val sslSocketFactory = sslContext.socketFactory
    val trustAllHostnames = HostnameVerifier { _, _ -> true }

    return OkHttpClient.Builder()
      .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
      .hostnameVerifier(trustAllHostnames)
      .build()
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
