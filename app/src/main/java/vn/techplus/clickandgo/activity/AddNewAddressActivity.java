package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Random;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.model.AddressAutoComplete;
import vn.techplus.clickandgo.rest.InvokeWS;
import vn.techplus.clickandgo.adapter.PlacesAutocompleteAdapter;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class AddNewAddressActivity extends Activity implements AdapterView.OnItemClickListener, PlacesAutocompleteAdapter.IHandleAddressAutoComplete {

    private AutoCompleteTextView mAutoCompOrigin, mAutoCompDestiantion;
    private Button mAddAddress;
    private TextView mCancel;
    private InvokeWS mInvokeWS;
    private String customerId;
    private static final String KEY = "userLI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);

        //Initialize
        mAutoCompOrigin = (AutoCompleteTextView) findViewById(R.id.autoCompleteOrigin);
        mAutoCompDestiantion = (AutoCompleteTextView) findViewById(R.id.autoCompleteDestination);
        mCancel = (TextView) findViewById(R.id.btnCancel);
        mAddAddress = (Button) findViewById(R.id.btnAddNewAddress);
        mInvokeWS = new InvokeWS(this);

        mAutoCompOrigin.setAdapter(new PlacesAutocompleteAdapter(this, R.layout.item_list_address));
        mAutoCompOrigin.setOnItemClickListener(this);

        mAutoCompDestiantion.setAdapter(new PlacesAutocompleteAdapter(this, R.layout.item_list_address));
        mAutoCompDestiantion.setOnItemClickListener(this);

        //get custom id from main activity
        SharedPreferences myprefsLg = getSharedPreferences(KEY, MODE_PRIVATE);
        customerId = myprefsLg.getString("customer_id", "");

        //Set event for click and selected
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Hiden key board
                InputMethodManager inputMethodManager = (InputMethodManager) AddNewAddressActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(AddNewAddressActivity.this.getCurrentFocus().getWindowToken(), 0);

                Random random = new Random();
                int number = random.nextInt(100000000);
                int reservationId = random.nextInt(100000000);
                String refTaxi = "sku_" + number;

                //Set params to send to server
                RequestParams params = new RequestParams();
                params.put("user_id", customerId);
                params.put("ref_taxi", refTaxi);
                params.put("reservation_id", reservationId);
                params.put("origin", mAutoCompOrigin.getText() + "");
                params.put("destination", mAutoCompDestiantion.getText() + "");

                mInvokeWS.restPostFavorites(params);
            }
        });
    }

    //get address at here
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //String str = (String) adapterView.getItemAtPosition(position);
    }

    @Override
    public void getListAddress(ArrayList<AddressAutoComplete> list) {

    }
}
