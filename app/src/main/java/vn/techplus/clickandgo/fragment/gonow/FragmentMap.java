package vn.techplus.clickandgo.fragment.gonow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.activity.SubGoNowActivity;
import vn.techplus.clickandgo.util.CheckInput;
import vn.techplus.clickandgo.util.GPSTracker;

/**
 * Created by ThanCV on 10/8/2015.
 */
public class FragmentMap extends Fragment {

    private View view;
    private GoogleMap googleMap;
    private GPSTracker gpsTracker;
    private double latitude;
    private double longtidue;
    private ImageView imgMarker;
    private Button btnCfmDeparture;
    private List<LatLng> listLatLong;
    private String address, state, addressB, stateB;
    private double latitudeA, latitudeB, longitudeA, longitudeB;
    private OnChangeLocationListener locationListener;
    private CheckInput checkInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Clear error duplicate id
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_map_layout, container, false);
            checkInput = new CheckInput();
        } catch (InflateException e) {
        }

        // Loading map
        initilizeMap();

        //init address for textview
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if (checkInput.ischeckinternet(getActivity())) {
                    getAddress(googleMap.getCameraPosition().target.latitude, googleMap.getCameraPosition().target.longitude);
                    locationListener.onChangeLocationDetail(address);
                    locationListener.onChangeLocation(state);
                } else {
                    locationListener.onChangeLocationDetail("No internet connection");
                    locationListener.onChangeLocation("");
                }
            }
        });

        //Handel event for button
        buttonHandel(view);

        return view;
    }

    //Connect with Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationListener = (OnChangeLocationListener) context;
    }

    public void buttonHandel(View view) {
        imgMarker = (ImageView) view.findViewById(R.id.imgMarker);
        btnCfmDeparture = (Button) view.findViewById(R.id.btnCfmDeparture);
        listLatLong = new ArrayList<LatLng>();

        btnCfmDeparture.setOnClickListener(new View.OnClickListener() {
            private double latitude;
            private double longitude;

            @Override
            public void onClick(View v) {
                imgMarker.setImageResource(R.drawable.marker_b);

                latitude = googleMap.getCameraPosition().target.latitude;
                longitude = googleMap.getCameraPosition().target.longitude;

                LatLng latLng = new LatLng(latitude, longitude);

                if (listLatLong.size() > 1) {
                    listLatLong.clear();
                    googleMap.clear();
                }

                listLatLong.add(latLng);
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLngMove = new LatLng(latLng.latitude, latLng.longitude + 0.0025);

                if (listLatLong.size() == 1) {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_a));
                    markerOptions.position(latLng);
                    googleMap.addMarker(markerOptions);
                    locationListener.flag(true);
                }
                if (listLatLong.size() == 2) {
                    imgMarker.setImageResource(R.drawable.marker_a);

                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_b));

                    //get coordinates from list
                    latitudeA = listLatLong.get(0).latitude;
                    longitudeA = listLatLong.get(0).longitude;

                    latitudeB = listLatLong.get(1).latitude;
                    longitudeB = listLatLong.get(1).longitude;

                    //get name address
                    getAddress(latitudeA, longitudeA);
                    getAddressB(latitudeB, longitudeB);

                    locationListener.flag(false);
                    googleMap.clear();

                    //send data to SubGoNow
                    Intent intent = new Intent(getActivity(), SubGoNowActivity.class);
                    if (state == null) {
                        state = "";
                    }
                    if (stateB == null) {
                        stateB = "";
                    }
                    intent.putExtra("start_address", address + ", " + state);
                    intent.putExtra("end_address", addressB + ", " + stateB);

                    intent.putExtra("latitudeA", latitudeA);
                    intent.putExtra("longitudeA", longitudeA);

                    intent.putExtra("latitudeB", latitudeB);
                    intent.putExtra("longitudeB", longitudeB);

                    startActivity(intent);
                }

                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLngMove));

            }
        });
    }

    //Initialize map
    private void initilizeMap() {

        gpsTracker = new GPSTracker(getActivity());

        if (googleMap == null) {
            SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
            googleMap = fragment.getMap();

            latitude = gpsTracker.getLatitude();
            longtidue = gpsTracker.getLongtidue();

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longtidue)).zoom(15).build();
            // start animation
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            //location button
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            //googleMap.getUiSettings().setZoomControlsEnabled(true);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getActivity(), "Sorry! unable to create maps",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //get address A
    private void getAddress(double latitude, double longtidue) {
        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longtidue, 1);

            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
                state = addresses.get(0).getAdminArea();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get address B
    private void getAddressB(double latitude, double longtidue) {
        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longtidue, 1);

            if (addresses.size() > 0) {
                addressB = addresses.get(0).getAddressLine(0);
                stateB = addresses.get(0).getAdminArea();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //interface listen location
    public interface OnChangeLocationListener {

        void onChangeLocationDetail(String locationDetail);

        void onChangeLocation(String location);

        void flag(Boolean flag);
    }

    @Override
    public void onResume() {
        super.onResume();

        //update camera for feature search address, get LatLng from SharedPreferences of restGetLogLatDetail(InvokeWS)
        SharedPreferences myprefsLg = getContext().getSharedPreferences("LatLng", getContext().MODE_PRIVATE);

        String lat = myprefsLg.getString("lat", "");
        String lng = myprefsLg.getString("lng", "");

        if ((lat != null && !lat.equals("")) && (lng != null && !lng.equals(""))) {
            double latitude = Double.valueOf(lat);
            double longitude = Double.valueOf(lng);

            //update camera when user selected address from search
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences myprefs = getContext().getSharedPreferences("LatLng", getContext().MODE_PRIVATE);
                myprefs.edit().clear().commit();
            }
        }, 2000);
    }
}
