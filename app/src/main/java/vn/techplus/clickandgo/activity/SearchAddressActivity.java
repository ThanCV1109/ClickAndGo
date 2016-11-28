package vn.techplus.clickandgo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.techplus.clickandgo.R;
import vn.techplus.clickandgo.adapter.PlacesAutocompleteAdapter;
import vn.techplus.clickandgo.model.AddressAutoComplete;
import vn.techplus.clickandgo.rest.InvokeWS;

/**
 * Created by ThanCV on 10/7/2015.
 */
public class SearchAddressActivity extends Activity implements AdapterView.OnItemClickListener, PlacesAutocompleteAdapter.IHandleAddressAutoComplete {

    private TextView mCancel;
    private AutoCompleteTextView mAutoCompView;
    private ArrayList<AddressAutoComplete> list = new ArrayList<>();
    private String mPlaceId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);

        mCancel = (TextView) findViewById(R.id.btnCancel);
        mAutoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        mAutoCompView.setAdapter(new PlacesAutocompleteAdapter(this, R.layout.item_list_address));
        mAutoCompView.setOnItemClickListener(this);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //get address at here
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //Hiden key board
        InputMethodManager inputMethodManager = (InputMethodManager) SearchAddressActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(SearchAddressActivity.this.getCurrentFocus().getWindowToken(), 0);

        //String address = (String) adapterView.getItemAtPosition(position);
        //get place id
        mPlaceId = this.list.get(position).getPlaceId();
        new InvokeWS(this).restGetLogLatDetail(mPlaceId);
    }

    @Override
    public void getListAddress(ArrayList<AddressAutoComplete> list) {
        this.list = list; //get list address from PlacesAutocompleteAdapter
    }
}

