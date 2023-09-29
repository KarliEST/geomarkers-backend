package com.locator.backend.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "location", schema = "public")
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "longitude")
    private double longitude;
    @Basic
    @Column(name = "latitude")
    private double latitude;
    @Basic
    @Column(name = "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && Double.compare(location.longitude, longitude) == 0 && Double.compare(location.latitude, latitude) == 0 && Objects.equals(description, location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, longitude, latitude, description);
    }
}
