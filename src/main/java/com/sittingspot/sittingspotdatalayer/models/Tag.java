package com.sittingspot.sittingspotdatalayer.models;

import jakarta.persistence.Embeddable;

@Embeddable
public record Tag(String key, String value) {
}
