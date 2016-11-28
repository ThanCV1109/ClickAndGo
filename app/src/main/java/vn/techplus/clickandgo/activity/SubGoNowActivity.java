package vn.techplus.clickandgo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.custom.SlideDateTimeListener;
import vn.techplus.clickandgo.custom.SlideDateTimePicker;
import vn.techplus.clickandgo.rest.InvokeWS;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class SubGoNowActivity extends AppCompatActivity {

    private SimpleDateFormat mFormatter = new SimpleDateFormat("dd-MM-yyyy/hh:mm:ss");
    private Button mDateTime;
    private TextView mShowDateTime;
    private TextView mDetailLocationA, mDetailLocationB;
    private double latitudeA, latitudeB, longitudeA, longitudeB;
    private String mTimeDate;
    private Button mViewPrices;
    private InvokeWS invokeWS;
    private TextView mCancelAll, mViewMap;
    private String start_address, end_address;
    private String url;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_go_now);

        mDetailLocationA = (TextView) findViewById(R.id.tvDetailLocationA);
        mDetailLocationB = (TextView) findViewById(R.id.tvDetailLocationB);
        mViewPrices = (Button) findViewById(R.id.btnViewPrices);

        mViewMap = (TextView) findViewById(R.id.btnViewMap);
        mDateTime = (Button) findViewById(R.id.btnEdit);
        mCancelAll = (TextView) findViewById(R.id.btnCancelAll);
        mShowDateTime = (TextView) findViewById(R.id.tvTime);

        //Check go now or book late
        //if flag = 1 -> booklate
        SharedPreferences getFlag = getSharedPreferences("FLAG", MODE_PRIVATE);
        flag = getFlag.getString("flag", "");
        if (("1").equals(flag)) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, 5);
            Date date = calendar.getTime();

            mTimeDate = mFormatter.format(date);
            mShowDateTime.setText(mTimeDate);
        }

        invokeWS = new InvokeWS(this);

        //Get data from FragmentMap
        Intent intent = getIntent();

        start_address = intent.getStringExtra("start_address");
        end_address = intent.getStringExtra("end_address");

        latitudeA = intent.getDoubleExtra("latitudeA", 0);
        longitudeA = intent.getDoubleExtra("longitudeA", 0);
        latitudeB = intent.getDoubleExtra("latitudeB", 0);
        longitudeB = intent.getDoubleExtra("longitudeB", 0);

        //Set text for textview location A, B
        mDetailLocationA.setText(start_address);
        mDetailLocationB.setText(end_address);

        mDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date()).build()
                        .show();
            }
        });

        mCancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubGoNowActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        mViewPrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimeDate == null) {
                    mTimeDate = mFormatter.format(new Date());
                }
                String startAddressParse = start_address.replace("/", "-");
                String endAddressParse = end_address.replace("/", "-");
                url = "http://www.wlc-preprod.com/clickndrive/requests/v.1/mobile/itinerary/calculate/" + startAddressParse + "/" + endAddressParse + "/" + mTimeDate;
                Log.e("getPrice", "" + url);
                //String urlParse = url.replaceAll(" ", "%20");

                invokeWS.restGetDistance(url);
            }
        });

        mViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubGoNowActivity.this, ViewMapActivity.class);
                intent.putExtra("latitudeA", latitudeA);
                intent.putExtra("longitudeA", longitudeA);

                intent.putExtra("latitudeB", latitudeB);
                intent.putExtra("longitudeB", longitudeB);
                startActivity(intent);
            }
        });

    }

    //Disable button back
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return false;
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            mTimeDate = mFormatter.format(date);
            mShowDateTime.setText(mTimeDate);
        }

        @Override
        public void onDateTimeCancel() {
            mTimeDate = mFormatter.format(new Date());
            mShowDateTime.setText(R.string.tv_go_now);
        }
    };

}
