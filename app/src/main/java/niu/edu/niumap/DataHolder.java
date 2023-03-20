package niu.edu.niumap;

import android.location.Location;

public class DataHolder {
    private Location location = new Location("cl");

    public Location getData() {return location;}
    public void setData(Location data) {this.location = data;}

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
}
