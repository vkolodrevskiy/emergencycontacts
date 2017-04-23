package com.fugru.util;

import java.io.IOException;

/**
 * Http client.
 *
 * @author vkolodrevskiy
 */
public interface HttpClient {
    /**
     * Perform GET request with the given url string.
     *
     * @param url
     * @return response as {@code String}.
     * @throws IOException
     */
    String doGet(String url) throws IOException;
}
