package vn.techplus.clickandgo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import vn.techplus.clickandgo.R;

/**
 * Created by ThanCV on 10/9/2015.
 */
public class MyCreditCardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private TextView myAccount;
    private Button mAddCreditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_credit_card);

        mAddCreditCard = (Button) findViewById(R.id.btnAdd);
        myAccount = (TextView) findViewById(R.id.btnMyAccount);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
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

        myAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MyCreditCardActivity.this, MyAccountActivity.class);
                startActivity(intent);
            }
        });

        mAddCreditCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCreditCardActivity.this, NewCreditCardActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_book) {
            finish();
            Intent intent = new Intent(MyCreditCardActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_my_trip) {
            finish();
            Intent intent = new Intent(MyCreditCardActivity.this, MyTripActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_credit_card) {
            mDrawerToggle.onDrawerClosed(mNavigationView);

        } else if (id == R.id.nav_favorite) {
            finish();
            Intent intent = new Intent(MyCreditCardActivity.this, MyFavoritesActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
