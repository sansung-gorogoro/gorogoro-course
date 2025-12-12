package com.lxp.course.common.event;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public record EventEnvelope<T extends DomainEvent>(
    UUID eventId,
    Instant occurredAt,
    T payload,
    Map<String, String> metadata
) {
    public EventEnvelope {
        Optional.ofNullable(eventId).orElseThrow(() -> new IllegalArgumentException("이벤트 Id 가 필요합니다."));
        Optional.ofNullable(occurredAt).orElseThrow(() -> new IllegalArgumentException("발생 시간이 필요합니다."));
        Optional.ofNullable(payload).orElseThrow(() -> new IllegalArgumentException("페이로드가 필요합니다."));
        metadata = metadata == null ? Collections.emptyMap() : Map.copyOf(metadata);
    }

    public static <T extends DomainEvent> EventEnvelope<T> wrap(T payload) {
        return new EventEnvelope<>(UUID.randomUUID(), Instant.now(), payload, Collections.emptyMap());
    }

    public EventEnvelope<T> withMetadata(Map<String, String> additional) {
        if (additional == null || additional.isEmpty()) {
            return this;
        }
        HashMap<String, String> merged = new HashMap<>(this.metadata());
        merged.putAll(additional);
        return new EventEnvelope<>(eventId, occurredAt, payload, merged);
    }

    public String type() {
        return payload.type();
    }

    public String version() {
        return payload.version();
    }
}
