package smb.pja.smbproject.fourth;

import android.location.Location;

public class Shop {

    private Integer id;
    private String name;
    private String description;
    private Double lat;
    private Double lon;
    private Integer radius;

    public Shop() {}

    public Shop(String name, String description, Double lat, Double lon, Integer radius) {
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
