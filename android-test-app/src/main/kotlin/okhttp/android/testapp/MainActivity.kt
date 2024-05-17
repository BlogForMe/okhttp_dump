/*
 * Copyright (C) 2023 Block, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okhttp.android.testapp

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import java.util.concurrent.Executors
import okhttp.android.testapp.databinding.ActivityMainBinding
import okhttp.android.testapp.databinding.ActivityOkhttpBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val request = OkHttpRequest()
    binding.btTestCache.setOnClickListener {
      Executors.newCachedThreadPool().submit {
        request.testCache(this)
      }
    }

    binding.btPostCache.setOnClickListener {
      request.postOkhttpCache(this)
    }
  }


  fun originRequest() {
    val client = OkHttpClient()

    val url = "https://api.github.com/repos/square/okhttp/contributors".toHttpUrl()
    println(url.topPrivateDomain())

    Log.d("MainActivity", "onCreate: ${Thread.currentThread().name}")
    client.newCall(Request(url)).enqueue(
      object : Callback {
        override fun onFailure(
          call: Call,
          e: IOException,
        ) {
          println("failed: $e")
        }

        override fun onResponse(
          call: Call,
          response: Response,
        ) {
//          println("response: ${response.code}")
          Log.d("MainActivity", "onResponse: ${Thread.currentThread().name}")
          Log.d("MainActivity", "response: ${response.body.string()}")


//          response.close()
        }
      }
    )
  }

}
