package com.example.google_maps;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends MainActivity implements OnMapReadyCallback,
        GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    private int tipoMapa;
    private Context context;
    private RequestQueue queue;
    private ContentActivity contentActivity;
    private ArrayList<ContentActivity> listCon;

    public MapActivity(Context ctx) {
        context = ctx;
        tipoMapa = 1;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setInfoWindowAdapter(MapActivity.this);

        stringRequest();

    }

    public void ConfiguracionMapa(View v) {
        if (tipoMapa < 4)
            tipoMapa = tipoMapa + 1;
        else
            tipoMapa = 1;

        mMap.setMapType(tipoMapa);

    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        Toast toast = Toast.makeText(context, "ver carta", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        return null;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        View infoView = LayoutInflater.from(context).inflate(R.layout.infor_main, null);
        TextView fname = (TextView) infoView.findViewById(R.id.facultyName);
        TextView fdecano = (TextView) infoView.findViewById(R.id.facultyDecano);
        TextView femail = (TextView) infoView.findViewById(R.id.facultyemail);
        TextView flocat = (TextView) infoView.findViewById(R.id.facultyLocation);
        ImageView flogo = (ImageView) infoView.findViewById(R.id.facultylogo);
        String url;

        //Objeto localización
        ContentActivity tmp = (ContentActivity) marker.getTag();
        fname.setText(tmp.getName());
        fdecano.setText(tmp.getAuthority());
        femail.setText(tmp.getContact());
        flocat.setText(tmp.getDirection());
        url=tmp.getLogo();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(flogo);

        return infoView;
    }

    public void stringRequest() {
        queue = Volley.newRequestQueue(context);
        String URL = "https://my-json-server.typicode.com/dennissezambrano2017/demo_json/db";
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("Map");

                            listCon = contentActivity.JsonObjectsBuild(jsonArray);
                            genericPoinst(listCon);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
    public void genericPoinst(ArrayList<ContentActivity> point) {


        for (int i = 0; i < point.size(); i++) {
            ContentActivity tmp = point.get(i);
            Marker map = mMap.addMarker(new
                    MarkerOptions().position(new LatLng(tmp.getLat(), tmp.getLng()))
                    .title(tmp.getName())
                    .snippet(tmp.getDirection()));
            map.setTag(tmp);
        }

    }
}
