package com.example.banking;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WireTransferServiceTest {

    @Test
    public void testProcessTransferPayloadWithRemediatedDependencies() throws Exception {
        WireTransferService service = new WireTransferService();
        String jsonPayload = "{\"transactionId\":\"TX-987654321\",\"amount\":150000.00,\"currency\":\"USD\"}";
        InputStream stream = new ByteArrayInputStream(jsonPayload.getBytes(StandardCharsets.UTF_8));

        String txId = service.processTransferPayload(stream);
        assertEquals("TX-987654321", txId);
    }
}
