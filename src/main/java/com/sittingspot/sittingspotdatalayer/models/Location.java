package com.sittingspot.sittingspotdatalayer.models;

import jakarta.persistence.Embeddable;

@Embeddable
public record Location(double x, double y) {
}
