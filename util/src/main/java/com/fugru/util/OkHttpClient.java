package com.fugru.util;

import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * {@link HttpClient} implementation based on OkHttp.
 *
 * @author vkolodrevskiy
 */
public class OkHttpClient implements HttpClient {
    @Override
    public String doGet(String url) throws IOException {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
