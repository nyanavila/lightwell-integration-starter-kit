package com.example.banking;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * High-criticality Wire Transfer Service.
 * Demonstrates that existing business logic and legacy API calls
 * remain 100% UNCHANGED when compiled against Lightwell remediated dependencies.
 */
public class WireTransferService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String processPaymentPayload(InputStream stream) throws Exception {
        // Uses Apache Commons IO 2.11.0 method signature directly
        String payload = IOUtils.toString(stream, StandardCharsets.UTF_8);
        return "PROCESSED: " + payload.length() + " bytes";
    }

    public int verifyFedwireConnection(String endpoint) throws Exception {
        // Uses legacy Apache HttpClient 4.5.12 API without refactoring
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(endpoint);
            return client.execute(request).getStatusLine().getStatusCode();
        }
    }
}
