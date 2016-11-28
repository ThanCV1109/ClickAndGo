package vn.techplus.clickandgo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.adapter.FragmentGoNowAdapter;
import vn.techplus.clickandgo.fragment.gonow.FragmentMap;
import vn.techplus.clickandgo.tabs.SlidingTabLayout;

public class GoNowMainActivity extends AppCompatActivity implements FragmentMap.OnChangeLocationListener {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private SlidingTabLayout mTabs;
    private Button mSearchAddress;
    private TextView mDetailLocation, mLocation;
    private ImageView mImgLocationAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_now_main);

        mImgLocationAB = (ImageView) findViewById(R.id.imgLocationAB);
        mDetailLocation = (TextView) findViewById(R.id.tvdetailLocation);
        mLocation = (TextView) findViewById(R.id.tvLocation);
        mSearchAddress = (Button) findViewById(R.id.btnSearch);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new FragmentGoNowAdapter(getSupportFragmentManager(), this));
        mViewPager.setOffscreenPageLimit(5);

        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tvTab);
        mTabs.setDistributeEvenly(true);
        mTabs.setViewPager(mViewPager);

        mSearchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoNowMainActivity.this, SearchAddressActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onChangeLocationDetail(String locationDetail) {
        mDetailLocation.setText(locationDetail);
    }

    //Get location from FragmentMap
    @Override
    public void onChangeLocation(String location) {
        mLocation.setText(location);
    }

    @Override
    public void flag(Boolean flag) {
        if (flag) {
            mImgLocationAB.setImageResource(R.drawable.marker_b);
        } else {
            mImgLocationAB.setImageResource(R.drawable.marker_a);
        }
    }
}
