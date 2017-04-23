package com.fugru.util;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * {@link CredentialsUtils} tests.
 *
 * @author vkolodrevskiy
 */
public class CredentialsUtilsTest {

    @Test
    public void getFromHeader() {
        Optional<Credentials> credentials =
                CredentialsUtils.getFromHeader("Basic Zm9vOmJhcg==");
        assertEquals(new Credentials("foo", "bar"), credentials.get());
    }

    @Test
    public void getFromHeaderEmptyHeader() {
        Optional<Credentials> credentials =
                CredentialsUtils.getFromHeader("");
        assertFalse(credentials.isPresent());

        credentials = CredentialsUtils.getFromHeader(null);
        assertFalse(credentials.isPresent());
    }

    @Test
    public void getFromHeaderInvalidHeader() {
        Optional<Credentials> credentials =
                CredentialsUtils.getFromHeader("This is invalid header.");
        assertFalse(credentials.isPresent());
    }
}
