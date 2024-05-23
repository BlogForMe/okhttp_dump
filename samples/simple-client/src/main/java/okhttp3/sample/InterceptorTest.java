package okhttp3.sample;


import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InterceptorTest {
  public static void main(String[] args) throws IOException {
    testApplicationInterceptors();
  }

  public static void testApplicationInterceptors() throws IOException {
    OkHttpClient client = new OkHttpClient.Builder()
      .addInterceptor(new LoggingInterceptor())
      .build();

    Request request = new Request.Builder()
      .url("http://www.publicobject.com/helloworld.txt")
      .header("User-Agent", "OkHttp Example")
      .build();

    Response response = client.newCall(request).execute();
    Objects.requireNonNull(response.body()).close();
  }

//  作者：柏油
//  链接：https://juejin.cn/post/7151761448757264415
//  来源：稀土掘金
//  著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}


class LoggingInterceptor implements Interceptor {
  @Override
  public Response intercept(Interceptor.Chain chain) throws IOException {
    Request request = chain.request();

    long t1 = System.nanoTime();

    System.out.println(String.format("LoggingInterceptor--- Sending request %s on %s%n%s",
      request.url(), chain.connection(), request.headers()));

    Response response = chain.proceed(request);

    long t2 = System.nanoTime();
    System.out.println(String.format("LoggingInterceptor--- Received response for %s in %.1fms%n%s",
      response.request().url(), (t2 - t1) / 1e6d, response.headers()));

    return response;
  }
}
