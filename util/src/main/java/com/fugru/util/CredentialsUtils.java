package com.fugru.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;
import java.util.StringTokenizer;

/**
 * Utility class to work with user credentials.
 *
 * @author vkolodrevskiy
 */
public class CredentialsUtils {
    private static final Logger log = LoggerFactory.getLogger(CredentialsUtils.class);

    public static Optional<Credentials> getFromHeader(String authHeader) {
        if (authHeader != null) {
            StringTokenizer st = new StringTokenizer(authHeader);
            if (st.hasMoreTokens()) {
                String basic = st.nextToken();
                if (basic.equalsIgnoreCase("Basic")) {
                    String credentials = new String(Base64.getDecoder().decode(st.nextToken()),
                            Charset.forName("UTF-8"));
                    log.debug("Credentials: " + credentials);
                    int p = credentials.indexOf(":");
                    if (p != -1) {
                        String login = credentials.substring(0, p).trim();
                        String password = credentials.substring(p + 1).trim();

                        return Optional.of(new Credentials(login, password));
                    } else {
                        log.error("Invalid authentication token.");
                    }
                }
            }
        }

        return Optional.empty();
    }
}
