package tanika.kulchutisin.kku.ac.th.mywitty;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFormSearch extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String name, fm, high ;
    String lat,lng;
    Double convertedLat , convertedLng;
    private String tel, address;
    Marker markMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_form_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = getIntent().getExtras().getString("lat");
        lng = getIntent().getExtras().getString("lng");
        fm = getIntent().getExtras().getString("fm");
        high = getIntent().getExtras().getString("high");
        tel = getIntent().getExtras().getString("tel");
        address = getIntent().getExtras().getString("location");

        StringBuilder bulid = new StringBuilder(lat);
        for (int i = 0 ; i < 9 ;i++)
        bulid.deleteCharAt(0);


        StringBuilder bulidja = new StringBuilder(lng);
        for (int i = 0 ; i < 9 ;i++)
            bulidja.deleteCharAt(0);



        name = getIntent().getExtras().getString("name");
        convertedLat = Double.parseDouble(bulid.toString());
        convertedLng = Double.parseDouble(bulidja.toString());

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        final LatLng location = new LatLng( convertedLat, convertedLng);
        MarkerOptions markerStation = new MarkerOptions();
        markerStation.position(location);
        markerStation.title(name+"\n"+fm);
        markerStation.icon(BitmapDescriptorFactory.fromResource(R.drawable.radio164));
        markMap = mMap.addMarker(markerStation);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
        markMap.showInfoWindow();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MapsFormSearch.this,popUpDetail.class);
                intent.putExtra("name",name);
                intent.putExtra("fm",fm);
                intent.putExtra("high", high);
                intent.putExtra("tel",tel);
                intent.putExtra("location",address);
                startActivity(intent);
                return true;
            }
        });

        drawCircle(new LatLng(convertedLat,convertedLng));

    }

    private void drawCircle(LatLng point){

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(point);

        // Radius of the circle
        circleOptions.radius(1000);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLUE);


        // Fill color of the circle
        circleOptions.fillColor(0x220000FF);

        // Border width of the circle
        circleOptions.strokeWidth(2);

        // Adding the circle to the GoogleMap
        mMap.addCircle(circleOptions);

    }
}
