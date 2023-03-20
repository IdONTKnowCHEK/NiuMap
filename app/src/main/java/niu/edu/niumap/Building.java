package niu.edu.niumap;

import java.util.Comparator;

public class Building {

    public String title, image, description;
    public double latitude, longitude;
    public int distance;

    public Building(){
    }

    public Building(String name, String image, int distance, String description, double latitude, double longitude) {
        this.title = name;
        this.image = image;
        this.distance = distance;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
