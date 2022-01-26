package com.example.google_maps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ContentActivity {
    private String Name;
    private String direction;
    private String authority;
    private String contact;
    private String logo;
    private Double lat;
    private Double lng;

    public ContentActivity() {
    }

    public ContentActivity(String name, String direction, String authority, String contact, String logo, double lat, double lng) {
        this.Name = name;
        this.direction = direction;
        this.authority = authority;
        this.contact = contact;
        this.logo = logo;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public static ArrayList<ContentActivity> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<ContentActivity> conte = new ArrayList<>();
        ArrayList <String> lis = new ArrayList<>();
        for (int i = 0; i < datos.length(); i++) {
            JSONObject user=  datos.getJSONObject(i);
            conte.add(new ContentActivity(user.getString("Name"),
                    user.getString("direction"),
                    user.getString("authority"),
                    user.getString("contact"),
                    user.getString("logo"),
                    user.getDouble("lat"),
                    user.getDouble("lng")));
        }
        return conte;
    }
}
