package vn.techplus.clickandgo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.rest.InvokeWS;
import vn.techplus.clickandgo.util.GPSTracker;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private Button myAccount, mGoNow, mBookLate;
    private GoogleMap mGoogleMap;
    private GPSTracker gpsTracker;
    private double mLatitude;
    private double mLongtidue;
    private String mFirstName, mLastName, mEmail, mGender, mCustomerId;
    private static final String KEY = "userLI";
    private InvokeWS mInvokeWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Handle data from Login
        handleData();

        //Handle data Favorite and Reservation
        handleDataFavorite();

        //get my location
        gpsTracker = new GPSTracker(this);

        myAccount = (Button) findViewById(R.id.btnMyAccount);
        mGoNow = (Button) findViewById(R.id.btnGoNow);
        mBookLate = (Button) findViewById(R.id.btnBookForLate);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerSlide(View drawerView, float slideOffset) {

                View container = findViewById(R.id.content_frame);
                container.setTranslationX(slideOffset * drawerView.getWidth());
            }

        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyAccountActivity.class);
                startActivity(intent);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                overridePendingTransition(R.anim.pull_in_left, 500);
            }
        });

        mGoNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences flag = getSharedPreferences("FLAG", MODE_PRIVATE);
                flag.edit().putString("flag", "0").commit();


                Intent intent = new Intent(MainActivity.this, GoNowMainActivity.class);
                startActivity(intent);
            }
        });

        mBookLate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences flag = getSharedPreferences("FLAG", MODE_PRIVATE);
                flag.edit().putString("flag", "1").commit();

                Intent intent = new Intent(MainActivity.this, GoNowMainActivity.class);
                startActivity(intent);
            }
        });

        if (checkGooglePlayServices()) {
            //Loading map
            loadingMap();
        }
    }

    private void handleDataFavorite() {
        SharedPreferences myprefsLg = getSharedPreferences("userLI", MODE_PRIVATE);
        String userId = myprefsLg.getString("customer_id", "");

        mInvokeWS = new InvokeWS(this);
        mInvokeWS.restGetDetailFavorite(userId);
        mInvokeWS.restGetReservationHistory(userId);
    }

    private void handleData() {
        //Get data from LogIn
        Intent intent = getIntent();
        mFirstName = intent.getStringExtra("firstname");
        mLastName = intent.getStringExtra("lastname");
        mEmail = intent.getStringExtra("email");
        mGender = intent.getStringExtra("gender");
        mCustomerId = intent.getStringExtra("customer_id");

        if (mFirstName != null) {
            //Save data from LogIn by SharedPreferences
            SharedPreferences myprefs = this.getSharedPreferences(KEY, MODE_PRIVATE);
            myprefs.edit().putString("firstname", mFirstName).commit();
            myprefs.edit().putString("lastname", mLastName).commit();
            myprefs.edit().putString("email", mEmail).commit();
            myprefs.edit().putString("gender", mGender).commit();
            myprefs.edit().putString("customer_id", mCustomerId).commit();
        } else {
            return;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_book) {
            mDrawerToggle.onDrawerClosed(mNavigationView);

        } else if (id == R.id.nav_my_trip) {
            Intent intent = new Intent(MainActivity.this, MyTripActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, 500);

        } else if (id == R.id.nav_credit_card) {
            Intent intent = new Intent(MainActivity.this, MyCreditCardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, 500);

        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MainActivity.this, MyFavoritesActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, 500);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Disable button back
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return false;
    }

    //Create map
    public void loadingMap() {
        // Loading map
        if (mGoogleMap == null) {
            mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            // check if map is created successfully or not
            if (mGoogleMap == null) {
                Toast.makeText(MainActivity.this, "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
        //Enable button search my location
        mGoogleMap.setMyLocationEnabled(true);

        mLatitude = gpsTracker.getLatitude();
        mLongtidue = gpsTracker.getLongtidue();

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(mLatitude, mLongtidue)).zoom(15).build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    //check google play services of device isUpdate
    private boolean checkGooglePlayServices() {
        final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            Log.e("GOOGLE NO", GooglePlayServicesUtil.getErrorString(status));

            // ask user to update google play services.
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, 1);
            dialog.show();
            return false;
        } else {
            Log.i("GOOGLE OK", GooglePlayServicesUtil.getErrorString(status));
            // google play services is updated.
            //your code goes here...
            return true;
        }
    }
}
