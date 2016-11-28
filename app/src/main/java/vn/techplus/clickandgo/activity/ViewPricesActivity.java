package vn.techplus.clickandgo.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.fragment.viewprices.FragmentMyself;
import vn.techplus.clickandgo.fragment.viewprices.FragmentOtherPassenger;

/**
 * Created by ThanCV on 10/10/2015.
 */
public class ViewPricesActivity extends AppCompatActivity {
    //* add by AnhTien
    private Button myself, ortherpass, mBack;
    private FragmentManager fragmentManager;
    private Fragment fragmentHolder;
    private FragmentMyself fragmentMysefl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prices);

        //Handle data
        handleData();

        //Initialize
        mBack = (Button) findViewById(R.id.btnBack);

        fragmentManager = getFragmentManager();
        fragmentHolder = fragmentManager.findFragmentById(R.id.fragment_holder3);
        subTrip();

        //set event click button
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void handleData() {
        Intent intent = getIntent();
        String origin = intent.getStringExtra("origin");
        String destination = intent.getStringExtra("destination");
        String distance = intent.getStringExtra("distance");
        String duration = intent.getStringExtra("duration");
        String duration_datetime = intent.getStringExtra("duration_datetime");
        String departure_date = intent.getStringExtra("departure_date");
        String departure_time = intent.getStringExtra("departure_time");
        String arrival_time = intent.getStringExtra("arrival_time").trim();

        Bundle bundle = new Bundle();
        bundle.putString("origin", origin);
        bundle.putString("destination", destination);
        bundle.putString("distance", distance);
        bundle.putString("duration", duration);
        bundle.putString("duration_datetime", duration_datetime);
        bundle.putString("departure_date", departure_date);
        bundle.putString("departure_time", departure_time);
        bundle.putString("arrival_time", arrival_time);

        fragmentMysefl = new FragmentMyself();
        fragmentMysefl.setArguments(bundle);
    }

    //* add by AnhTien
    private void subTrip() {
        myself = (Button) findViewById(R.id.btn_myself);
        ortherpass = (Button) findViewById(R.id.btn_otherpassenger);
        myself.setOnClickListener(subClick);
        ortherpass.setOnClickListener(subClick);
        myself.performClick();
    }

    //* add by AnhTien
    private OnClickListener subClick = new subtripClick();

    private class subtripClick implements OnClickListener {

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (v.getId() == R.id.btn_myself) {
                myself.setSelected(true);
                ortherpass.setSelected(false);

                transaction.setTransition(FragmentTransaction.TRANSIT_UNSET);
                transaction.replace(R.id.fragment_holder3, fragmentMysefl);
                transaction.addToBackStack(FragmentMyself.class.getSimpleName());
                transaction.commit();
            } else {
                myself.setSelected(false);
                ortherpass.setSelected(true);
                transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                transaction.replace(R.id.fragment_holder3, new FragmentOtherPassenger());
                transaction.addToBackStack(FragmentOtherPassenger.class.getSimpleName());
                transaction.commit();

            }
        }
    }

}
