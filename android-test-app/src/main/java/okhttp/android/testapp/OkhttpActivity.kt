package okhttp.android.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import okhttp.android.testapp.databinding.ActivityOkhttpBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class OkhttpActivity : ComponentActivity() {

  val client = OkHttpClient()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val binding = ActivityOkhttpBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btGet.setOnClickListener {
      getRequest()
    }
  }

  fun getRequest(){
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

}
