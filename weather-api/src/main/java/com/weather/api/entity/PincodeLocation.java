package com.weather.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Stores pincode with latitude and longitude to avoid repeated Geocoding API calls
@Getter
@Setter
@Entity
@Table(name = "pincode_location")
public class PincodeLocation {

    @Id
    private String pincode;

    private Double latitude;
    private Double longitude;
}