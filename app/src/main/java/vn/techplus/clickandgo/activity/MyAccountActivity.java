package vn.techplus.clickandgo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.techplus.clickandgo.R;

/**
 * Created by ThanCV on 10/8/2015.
 */
public class MyAccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private Button myAccount;
    private TextView mLogOut;
    private TextView mModify;
    private TextView mName, mEmail;
    private static final String KEY = "userLI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        mName = (TextView) findViewById(R.id.tvName);
        mEmail = (TextView) findViewById(R.id.tvEmail);

        //Get data for infomation account
        handleData();

        //Initialize
        mModify = (TextView) findViewById(R.id.btnModify);
        myAccount = (Button) findViewById(R.id.btnMyAccount);
        mLogOut = (TextView) findViewById(R.id.btnLogOut);

        //Invoke toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //settup for navigaition drawer layput
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

        //Set event for action click
        mModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this, ModifyPassengerActivity.class);
                startActivity(intent);
            }
        });

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyAccountActivity.this)
                        .setTitle("Log Out")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences myprefsLg = getSharedPreferences(KEY, MODE_PRIVATE);
                                myprefsLg.edit().clear().commit();

                                Intent intent = new Intent(MyAccountActivity.this, SplashScreenActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_book) {
            Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_my_trip) {
            Intent intent = new Intent(MyAccountActivity.this, MyTripActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_credit_card) {
            Intent intent = new Intent(MyAccountActivity.this, MyCreditCardActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MyAccountActivity.this, MyFavoritesActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void handleData() {
        //Get data from LogIn
        SharedPreferences myprefsLg = getSharedPreferences(KEY, MODE_PRIVATE);
        String firstname = myprefsLg.getString("firstname", "");
        String lastname = myprefsLg.getString("lastname", "");
        String name = firstname + " " + lastname;
        String email = myprefsLg.getString("email", "");

        //Set data for name and email from LogIn
        mName.setText(name);
        mEmail.setText(email);
    }

}
