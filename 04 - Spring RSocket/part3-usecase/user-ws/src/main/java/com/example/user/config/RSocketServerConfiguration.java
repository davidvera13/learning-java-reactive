package com.example.user.config;

import com.example.user.domain.enums.OperationType;
import io.rsocket.metadata.WellKnownMimeType;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

public class RSocketServerConfiguration {
    @Bean
    public RSocketStrategiesCustomizer strategiesCustomizer() {
        MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.APPLICATION_CBOR.getString());
        return strategies -> strategies.metadataExtractorRegistry(
                metadataExtractorRegistry -> metadataExtractorRegistry
                        .metadataToExtract(mimeType, OperationType.class, "operation-type")
        );
    }
}
