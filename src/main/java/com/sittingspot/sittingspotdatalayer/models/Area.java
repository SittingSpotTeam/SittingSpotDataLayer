package com.sittingspot.sittingspotdatalayer.models;

import jakarta.persistence.Embeddable;

@Embeddable
public record Area(Location center, double range) {
}
