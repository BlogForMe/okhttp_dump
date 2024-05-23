/*
 * Copyright (C) 2013 Square, Inc.
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
package okhttp3.sample;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.jetbrains.annotations.NotNull;

public class OkHttpContributors {
  private static final String ENDPOINT = "https://api.github.com/repos/square/okhttp/contributors";
  private static final Moshi MOSHI = new Moshi.Builder().build();
  private static final JsonAdapter<List<Contributor>> CONTRIBUTORS_JSON_ADAPTER = MOSHI.adapter(
    Types.newParameterizedType(List.class, Contributor.class));

  static class Contributor {
    String login;
    int contributions;
  }

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String... args) throws Exception {
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(null).addNetworkInterceptor(null).build();

    // Create request for remote resource.
    Request request = new Request.Builder()
      .url(ENDPOINT)
      .build();

    Callback callback = new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {

      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String name = Thread.currentThread().getName();
        System.out.println("response " + name);
      }
    };

    for (int i = 0; i < 20; i++) {
      client.newCall(request).enqueue(callback);
    }

//    Request request1 = new Request.Builder()
//      .url("https://www.unicode.org/reports/tr46/#IDNA_Mapping_Table")
//      .build();
    //for (int i = 0; i < 10; i++) {
//      client.newCall(request1).enqueue(callback);
    //}
  }

  private OkHttpContributors() {
    // No instances.
  }


}
