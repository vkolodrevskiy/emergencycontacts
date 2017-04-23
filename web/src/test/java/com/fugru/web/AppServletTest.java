package com.fugru.web;

import com.fugru.util.VCardUtils;
import com.fugru.util.HttpClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Scanner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * {@link AppServlet} tests.
 *
 * @author vkolodrevskiy
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AppServlet.class, LoggerFactory.class})
public class AppServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpClient httpClient;

    private static String vCard;
    private static Logger logger;

    @BeforeClass
    public static void setupClass() {
        mockStatic(LoggerFactory.class);
        logger = mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);

        vCard = readFile("vcard-sample.vcf");
    }

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        // with mock static we should reset before each test run
        reset(logger);

        when(httpClient.doGet(anyString())).thenReturn(vCard);
    }

    @Test
    public void doGet() throws Exception {
        when(request.getHeader(eq("Authorization"))).thenReturn("Basic Zm9vOmJhcg==");

        AppServlet servlet = new AppServlet(httpClient);
        servlet.doGet(request, response);

        verify(logger).info("User: {}.", "foo");
        verify(logger).info("Password: {}.", "bar");
        verify(logger).info("VCard: {}.", VCardUtils.vcardToJson(vCard, true));
    }

    @Test
    public void doGetCredentialsNotPresent() throws Exception {
        when(request.getHeader(eq("Authorization"))).thenReturn("");

        AppServlet servlet = new AppServlet(httpClient);
        servlet.doGet(request, response);

        verify(logger).error("Credentials are not present.");
        verify(logger).info("VCard: {}.", VCardUtils.vcardToJson(vCard, true));
    }

    private static String readFile(String fileName) {
        Scanner scanner = new Scanner(AppServletTest.class.getClassLoader().getResourceAsStream(fileName));
        String contents = scanner.useDelimiter("\\A").next();
        scanner.close();
        return contents;
    }
}
