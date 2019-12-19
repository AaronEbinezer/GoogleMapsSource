package com.eagleemperor.googlemapssource;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Aaron on 7/11/2018.
 */

public class PlaceInfo {
    String name;
    String address;
    LatLng latlng;

    public PlaceInfo(String name, String address, LatLng latitude) {
        this.name = name;
        this.address = address;
        this.latlng = latitude;
    }
    public PlaceInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }


    @Override
    public String toString() {
        return "PlaceInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latlng='" + latlng + '\'' +
                '}';
    }
}
