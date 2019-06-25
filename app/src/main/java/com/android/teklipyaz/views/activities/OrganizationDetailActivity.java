package com.android.teklipyaz.views.activities;

import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.teklipyaz.R;
import com.android.teklipyaz.models.entities.ImageModel;
import com.android.teklipyaz.models.entities.OrganizationModel;
import com.android.teklipyaz.views.adapters.OrganizationDetailSliderAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;
//import com.viewpagerindicator.CirclePageIndicator;

public class OrganizationDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String  TAG = "DETAIL";
    private ViewPager sliderView;
    private LocationManager locationManager;
    private MapView mapView;
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private String lat = "";
    private String lng = "";
    private String empty = "-";
    private int zoom = 15;
    TextView title;
    private String categorycity = "";
    TextView category;
    TextView description;
    TextView show_more;
    TextView show_less;
    TextView address;
    TextView phone;
    TextView work_time;
    TextView website;
    FrameLayout back_button;
    //String images[] = {"Organizations/Organization408/25da51.jpg","Organizations/Organization408/25da51.jpg"};
    String [] images;
    private OrganizationModel item;
    OrganizationDetailSliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_detail);

        /*TextView text = (TextView) findViewById(R.id.title);*/

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            //String id2 = arguments.get("organization_id").toString();
            Gson gson = new Gson();
            item = gson.fromJson(getIntent().getStringExtra("myJsonData"), OrganizationModel.class);
            /*Toast.makeText(getApplicationContext(),
                    "ID: " + id+" ID2: " + id2, Toast.LENGTH_LONG)
                    .show();
            text.setText("My ID: "+id+" ID2: " + id2);*/
        }

        //work with slider start
        List<ImageModel> imagesList = item.getImages();
        int imagesListSize = imagesList.size();
        if(imagesList != null && imagesListSize > 0){
            images = new String[imagesListSize];
            for (int i=0; i<imagesListSize; i++) {
                images[i] = imagesList.get(i).getFilepath();
            }

            sliderView = (ViewPager)findViewById(R.id.co_slider);
            sliderAdapter = new OrganizationDetailSliderAdapter(OrganizationDetailActivity.this, images);
            sliderView.setAdapter(sliderAdapter);
            TabLayout dots = (TabLayout)findViewById(R.id.dots);
            dots.setupWithViewPager(sliderView, true);
        }
        //work with slider end

        title = (TextView) findViewById(R.id.title);
        category = (TextView) findViewById(R.id.category);
        description = (TextView) findViewById(R.id.description);
        address = (TextView) findViewById(R.id.address);
        phone = (TextView) findViewById(R.id.phone);
        work_time = (TextView) findViewById(R.id.work_time);
        website = (TextView) findViewById(R.id.website);
        show_more = (TextView) findViewById(R.id.show_more);
        show_less = (TextView) findViewById(R.id.show_less);
        //Log.d(TAG, "Get resource: "+getResources().getInteger(R.integer.description_line));

        if (item != null){
            if(item.getTitle() != null && item.getTitle().trim().length() > 0)
                title.setText(item.getTitle());
            else
                title.setVisibility(View.GONE);

            if(item.getDescription() != null && item.getDescription().trim().length() > 0) {
                description.setText(item.getDescription());
                description.post(new Runnable() {
                    @Override
                    public void run() {
                        if( description.getLineCount() <= getResources().getInteger(R.integer.description_line) )
                            show_more.setVisibility(View.GONE);
                    }
                });

            }
            else {
                description.setVisibility(View.GONE);
                show_more.setVisibility(View.GONE);
            }

            if(item.getCategoryId() != null && item.getCategoryId().trim().length() > 0)
                categorycity = item.getCategoryId().trim();

            if(item.getCityId() != null && item.getCityId().trim().length() > 0) {
                if(!categorycity.isEmpty())
                    categorycity += " | ";
                categorycity += item.getCityId().trim();
            }

            if(!categorycity.isEmpty())
                category.setText(categorycity);
            else
                category.setVisibility(View.GONE);

            if(item.getAddress() != null && item.getAddress().trim().length() > 0) {
                address.setText(item.getAddress());
                Linkify.addLinks(address, Linkify.MAP_ADDRESSES);
            }
            else
            {
                address.setVisibility(View.GONE);
                findViewById(R.id.address_line).setVisibility(View.GONE);
            }

            if(item.getTelephone() != null && item.getTelephone().trim().length() > 0) {
                phone.setText(item.getTelephone());
                Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
            }
            else {
                phone.setVisibility(View.GONE);
                findViewById(R.id.phone_line).setVisibility(View.GONE);
            }

            if(item.getWorkingTime() != null && item.getWorkingTime().trim().length() > 0)
                work_time.setText(item.getWorkingTime());
            else {
                work_time.setVisibility(View.GONE);
                findViewById(R.id.work_time_line).setVisibility(View.GONE);
            }

            if(item.getWebsite() != null && item.getWebsite().trim().length() > 0) {
                website.setText(item.getWebsite());
                Linkify.addLinks(website, Linkify.WEB_URLS);
            }
            else {
                website.setVisibility(View.GONE);
                findViewById(R.id.website_line).setVisibility(View.GONE);
            }

            if(item.getLatitude() != null && item.getLatitude().trim().length() > 0 && item.getLongitude() != null && item.getLongitude().trim().length() > 0){
                lat = item.getLatitude().trim();
                lng = item.getLongitude().trim();
            }
        }

        show_more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                show_more.setVisibility(View.GONE);
                show_less.setVisibility(View.VISIBLE);
                description.setMaxLines(Integer.MAX_VALUE);
            }
        });

        show_less.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                show_more.setVisibility(View.VISIBLE);
                show_less.setVisibility(View.GONE);
                description.setMaxLines(getResources().getInteger(R.integer.description_line));
            }
        });

        FrameLayout back_button = (FrameLayout)findViewById(R.id.close_button);
        back_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //work with map start
        if(lat.isEmpty() && lng.isEmpty()) {
            //hide google map
            hideGoogleMap();
        }
        else{

            int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
            // Showing status
            if(status == ConnectionResult.SUCCESS) {
                try {
                    MapsInitializer.initialize(this);
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                } catch (Exception e) {
                    Toast.makeText(this.getApplicationContext(), "Map not supported", Toast.LENGTH_LONG).show();
                }
            }
            else{
                hideGoogleMap();
            }
        }
        //work with map end
    }

    private void hideGoogleMap(){
        FrameLayout layout = (FrameLayout)findViewById(R.id.co_map_outer);
        layout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        loadMap(googleMap);

    }

    protected void loadMap(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));

        // Moving CameraPosition to last clicked position
        LatLng coordinate = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate) // Center Set
                .zoom(zoom)      // Zoom
                .bearing(90)      // Orientation of the camera to east
                .tilt(30)         // Tilt of the camera to 30 degrees
                .build();         // Creates a CameraPosition from the builder
        //map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        if(!item.getTitle().isEmpty())
            markerOptions.title(item.getTitle());

        if(!categorycity.isEmpty())
            markerOptions.snippet(categorycity);

        // Adding marker on the Google Map
        googleMap.addMarker(markerOptions);
    }
}