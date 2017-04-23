package com.fugru.web;

import com.fugru.util.Credentials;
import com.fugru.util.CredentialsUtils;
import com.fugru.util.VCardUtils;
import com.fugru.util.OkHttpClient;
import com.fugru.util.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Main application servlet.
 *
 * @author vkolodrevskiy
 */
public class AppServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AppServlet.class);
    private HttpClient httpClient;

    public AppServlet() {
        this.httpClient = new OkHttpClient();
    }

    // for tests
    public AppServlet(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getCredentials(request);
        getVCard();
    }

    private void getCredentials(HttpServletRequest request) {
        Optional<Credentials> credentials = CredentialsUtils.getFromHeader(request.getHeader("Authorization"));
        if (credentials.isPresent()) {
            log.info("User: {}.", credentials.get().getLogin());
            log.info("Password: {}.", credentials.get().getPassword());
        } else {
            log.error("Credentials are not present.");
        }
    }

    private void getVCard() throws IOException {
        try {
            String vCard = VCardUtils.vcardToJson(
                    httpClient.doGet("http://dev.dublabs.com:8000/mobileCampus/json/emergencyContacts"), true);
            log.info("VCard: {}.", vCard);
        } catch (IOException e) {
            log.error("Failed to download VCard.");
        }
    }
}
